package com.jamshedalamqaderi.appwrite.kmp.services

import co.touchlab.kermit.Logger
import com.jamshedalamqaderi.appwrite.kmp.Client
import com.jamshedalamqaderi.appwrite.kmp.Service
import com.jamshedalamqaderi.appwrite.kmp.exceptions.AppwriteException
import com.jamshedalamqaderi.appwrite.kmp.extensions.forEachAsync
import com.jamshedalamqaderi.appwrite.kmp.extensions.fromJson
import com.jamshedalamqaderi.appwrite.kmp.extensions.jsonCast
import com.jamshedalamqaderi.appwrite.kmp.models.RealtimeCallback
import com.jamshedalamqaderi.appwrite.kmp.models.RealtimeResponse
import com.jamshedalamqaderi.appwrite.kmp.models.RealtimeResponseEvent
import com.jamshedalamqaderi.appwrite.kmp.models.RealtimeSubscription
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readReason
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import kotlin.coroutines.CoroutineContext

class Realtime(client: Client) : Service(client), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private companion object {
        private const val TYPE_ERROR = "error"
        private const val TYPE_EVENT = "event"

        private const val DEBOUNCE_MILLIS = 1L

        private var session: DefaultClientWebSocketSession? = null
        private var activeChannels = mutableSetOf<String>()
        private var activeSubscriptions = mutableMapOf<Int, RealtimeCallback<JsonElement>>()

        private var subCallDepth = 0
        private var reconnectAttempts = 0
        private var subscriptionsCounter = 0
        private var reconnect = true
    }

    private suspend fun createSession() {
        if (activeChannels.isEmpty()) {
            reconnect = false
            closeSession()
            return
        }
        if (session != null) {
            reconnect = false
            closeSession()
        }
        val endpoint = client.endpointRealtime ?: ""
        val realtimeEndpoint =
            if (endpoint.endsWith("/")) {
                "${endpoint}realtime"
            } else {
                "$endpoint/realtime"
            }
        session =
            client.createOrGetClient().webSocketSession {
                url(realtimeEndpoint)
                parameter("project", client.config["project"])
                activeChannels.forEach { channel ->
                    parameter("channels[]", channel)
                }
                method = HttpMethod.Get
            }
        if (session != null) {
            reconnectAttempts = 0
        }
        try {
            sessionLoop()
        } catch (e: Exception) {
            Logger.e { "Exception occurred on Session Loop: ${e.message}" }
        }
    }

    private suspend fun closeSession() {
        session?.close()
        session = null
    }

    private fun getTimeout() =
        when {
            reconnectAttempts < 5 -> 1000L
            reconnectAttempts < 15 -> 5000L
            reconnectAttempts < 100 -> 10000L
            else -> 60000L
        }

    fun subscribe(
        vararg channels: String,
        callback: (RealtimeResponseEvent<JsonElement>) -> Unit,
    ): RealtimeSubscription {
        val counter = subscriptionsCounter++
        activeChannels.addAll(channels)
        activeSubscriptions[counter] =
            RealtimeCallback(
                channels.toList(),
                JsonElement.serializer(),
                callback,
            )
        launch {
            subCallDepth++
            delay(DEBOUNCE_MILLIS)
            if (subCallDepth == 1) {
                createSession()
            }
            subCallDepth--
        }
        return RealtimeSubscription {
            activeSubscriptions.remove(counter)
            cleanUp(*channels)
            launch {
                createSession()
            }
        }
    }

    private suspend fun sessionLoop() {
        while (true) {
            if (!isActive) {
                break
            }
            when (val frame = session?.incoming?.receive()) {
                is Frame.Binary -> {}
                is Frame.Close -> {
                    if (!reconnect) {
                        reconnect = true
                        break
                    }

                    val timeout = getTimeout()
                    val reason = frame.readReason()
                    Logger.e(
                        tag = this@Realtime::class.simpleName ?: "",
                        AppwriteException(
                            message = reason?.message ?: "Realtime disconnected.",
                            code = reason?.code?.toInt(),
                        ),
                    ) {
                        "Realtime disconnected. Re-connecting in ${timeout / 1000} seconds."
                    }
                    coroutineScope {
                        launch {
                            delay(timeout)
                            reconnectAttempts++
                            createSession()
                        }
                    }
                }

                is Frame.Ping -> {}
                is Frame.Pong -> {}
                is Frame.Text -> {
                    val message = frame.readText()
                    val response =
                        message
                            .fromJson(RealtimeResponse.serializer(JsonElement.serializer()))
                    when (response.type) {
                        TYPE_ERROR -> handleResponseError(response)
                        TYPE_EVENT -> handleResponseEvent(response)
                    }
                }

                else -> {}
            }
        }
    }

    private fun handleResponseError(message: RealtimeResponse<JsonElement>) {
        throw message.data.jsonCast(JsonElement.serializer(), AppwriteException.serializer())
    }

    private suspend fun handleResponseEvent(message: RealtimeResponse<JsonElement>) {
        val event =
            message.data.jsonCast(
                JsonElement.serializer(),
                RealtimeResponseEvent.serializer(JsonElement.serializer()),
            )
        if (event.channels.isEmpty()) {
            return
        }
        if (!event.channels.any { activeChannels.contains(it) }) {
            return
        }
        activeSubscriptions.values.forEachAsync { subscription ->
            if (event.channels.any { subscription.channels.contains(it) }) {
                subscription.callback(event)
            }
        }
    }

    private fun cleanUp(vararg channels: String) {
        activeChannels.removeAll { channel ->
            if (!channels.contains(channel)) {
                return@removeAll false
            }
            activeSubscriptions.values.none { callback ->
                callback.channels.contains(channel)
            }
        }
    }
}
