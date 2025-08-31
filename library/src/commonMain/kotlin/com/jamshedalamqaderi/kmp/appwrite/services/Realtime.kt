package com.jamshedalamqaderi.kmp.appwrite.services

import co.touchlab.kermit.Logger
import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.ID
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.extensions.forEachAsync
import com.jamshedalamqaderi.kmp.appwrite.extensions.fromJson
import com.jamshedalamqaderi.kmp.appwrite.extensions.jsonCast
import com.jamshedalamqaderi.kmp.appwrite.models.RealtimeCallback
import com.jamshedalamqaderi.kmp.appwrite.models.RealtimeCallbackFunction
import com.jamshedalamqaderi.kmp.appwrite.models.RealtimeCode
import com.jamshedalamqaderi.kmp.appwrite.models.RealtimeResponse
import com.jamshedalamqaderi.kmp.appwrite.models.RealtimeResponseEvent
import com.jamshedalamqaderi.kmp.appwrite.models.RealtimeSubscription
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.util.collections.ConcurrentMap
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import kotlin.coroutines.CoroutineContext

@OptIn(FlowPreview::class)
class Realtime(client: Client) : Service(client), CoroutineScope {
    companion object {
        private const val DEBOUNCE_MILLIS = 1L
        private const val HEARTBEAT_INTERVAL = 20_000L
    }

    private val logger = Logger.withTag(this::class.simpleName.toString())
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job
    private var heartbeatJob: Job? = null
    private val subscriptionRequests = MutableSharedFlow<Unit>(0, 10)

    init {
        subscriptionRequests
            .debounce(DEBOUNCE_MILLIS)
            .onEach { createSession() }
            .launchIn(this)
    }

    private var session: DefaultClientWebSocketSession? = null
    private val activeSubscriptions = ConcurrentMap<String, RealtimeCallback>()

    private var reconnect = true
    private var reconnectAttempts = 0

    fun subscribe(
        vararg channels: String,
        callback: RealtimeCallbackFunction<JsonElement>,
    ): RealtimeSubscription {
        val subscriptionId = ID.unique()
        activeSubscriptions[subscriptionId] =
            RealtimeCallback(
                channels = channels.toSet(),
                callback = callback,
            )
        if (!subscriptionRequests.tryEmit(Unit)) {
            throw AppwriteException("Failed to subscribe to channels")
        }
        return RealtimeSubscription {
            activeSubscriptions.remove(subscriptionId)
            subscriptionRequests.tryEmit(Unit)
        }
    }

    private suspend fun createSession() {
        val activeChannels = activeSubscriptions.values.flatMap { it.channels }.toSet()
        if (activeChannels.isEmpty()) {
            reconnect = false
            closeSession()
            return
        }
        if (session != null) {
            reconnect = false
            closeSession()
        }
        val endpoint = client.endpointRealtime?.trimEnd('/') ?: throw AppwriteException("Realtime endpoint is null")
        val realtimeEndpoint = "$endpoint/realtime"
        client.createOrGetClient().webSocket({
            url(realtimeEndpoint)
            parameter("project", client.config["project"])
            activeChannels.forEach { channel ->
                parameter("channels[]", channel)
            }
            method = HttpMethod.Get
        }) {
            session = this
            reconnectAttempts = 0
            startHeartbeat()
            socketSessionLoop(activeChannels.toList())
        }
    }

    private suspend fun closeSession() {
        stopHeartbeat()
        session?.close(
            CloseReason(
                CloseReason.Codes.NORMAL,
                "Closed by client: ${RealtimeCode.POLICY_VIOLATION.value}",
            ),
        )
        session = null
    }

    private fun startHeartbeat() {
        stopHeartbeat()
        heartbeatJob =
            launch {
                while (isActive) {
                    if (session == null) break
                    delay(HEARTBEAT_INTERVAL)
                    runCatching { session?.send("""{"type":"ping"}""") }.onFailure {
                        logger.e(it) { "Heartbeat failed" }
                    }
                }
            }
    }

    private fun stopHeartbeat() {
        heartbeatJob?.cancel()
        heartbeatJob = null
    }

    private fun getTimeout(): Long {
        return when {
            reconnectAttempts < 5 -> 1000L
            reconnectAttempts < 15 -> 5000L
            reconnectAttempts < 100 -> 10000L
            else -> 60000L
        }
    }

    private suspend fun DefaultClientWebSocketSession.socketSessionLoop(activeChannels: List<String>) {
        incoming.consumeAsFlow()
            .mapNotNull { it as? Frame.Text }
            .map { it.readText() }
            .map { it.fromJson<RealtimeResponse<JsonElement>>() }
            .onEach { response ->
                when (response.type) {
                    "error" -> {
                        response.data?.jsonCast(
                            JsonElement.serializer(),
                            AppwriteException.serializer(),
                        )?.let { throw it }
                    }

                    "event" -> {
                        launch {
                            val event =
                                response.data?.jsonCast(
                                    JsonElement.serializer(),
                                    RealtimeResponseEvent.serializer(JsonElement.serializer()),
                                ) ?: return@launch
                            if (event.channels.isEmpty()) return@launch
                            if (!event.channels.any { activeChannels.contains(it) }) return@launch
                            activeSubscriptions.values.forEachAsync { subscription ->
                                if (event.channels.any { subscription.channels.contains(it) }) {
                                    subscription.callback(event)
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
            .catch {
                logger.e(it) { "Error while parsing event response" }
            }
            .onCompletion { cause ->
                stopHeartbeat()
                if (!reconnect) {
                    reconnect = true
                    return@onCompletion
                }
                val timeout = getTimeout()
                logger.e(cause) { "Realtime disconnected. Retrying in $timeout ms." }
                delay(timeout)
                reconnectAttempts++
                subscriptionRequests.tryEmit(Unit)
            }
            .collect()
    }
}
