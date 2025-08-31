package com.jamshedalamqaderi.kmp.appwrite

import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.extensions.encodeUnknownValue
import com.jamshedalamqaderi.kmp.appwrite.models.Progress
import com.jamshedalamqaderi.kmp.appwrite.stores.CachedCookiesStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.onDownload
import io.ktor.client.plugins.onUpload
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.pingInterval
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

class Client(
    var endpoint: String = "https://cloud.appwrite.io/v1",
    var endpointRealtime: String? = null,
    private var selfSigned: Boolean = false,
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    internal lateinit var http: HttpClient
    private var updated: Boolean = false

    val headers: MutableMap<String, String> = defaultHeaders()

    val config: MutableMap<String, String> = mutableMapOf()

    init {
        updated = true
        setSelfSigned(selfSigned)
        onClientInit()
    }

    /**
     * Set Project
     *
     * Your project ID
     *
     * @param {string} project
     *
     * @return this
     */
    fun setProject(value: String): Client {
        config["project"] = value
        addHeader("x-appwrite-project", value)
        return this
    }

    /**
     * Set Key
     *
     * Your secret API key
     *
     * @param {string} key
     *
     * @return this
     */
    fun setKey(value: String): Client {
        if (value.isEmpty()) return this
        config["key"] = value
        addHeader("x-appwrite-key", value)
        return this
    }

    /**
     * Set JWT
     *
     * Your secret JSON Web Token
     *
     * @param {string} jwt
     *
     * @return this
     */
    fun setJWT(value: String): Client {
        config["jWT"] = value
        addHeader("x-appwrite-jwt", value)
        return this
    }

    /**
     * Set Locale
     *
     * @param {string} locale
     *
     * @return this
     */
    fun setLocale(value: String): Client {
        config["locale"] = value
        addHeader("x-appwrite-locale", value)
        return this
    }

    /**
     * Set Session
     *
     * The user session to authenticate with
     *
     * @param {string} session
     *
     * @return this
     */
    fun setSession(value: String): Client {
        config["session"] = value
        addHeader("x-appwrite-session", value)
        return this
    }

    /**
     * Set self Signed
     *
     * @param status
     *
     * @return this
     */
    fun setSelfSigned(status: Boolean): Client {
        selfSigned = status
        updated = true
        return this
    }

    /**
     * Set endpoint and realtime endpoint.
     *
     * @param endpoint
     *
     * @return this
     */
    fun setEndpoint(endpoint: String): Client {
        this.endpoint = endpoint

        if (this.endpointRealtime == null && endpoint.startsWith("http")) {
            this.endpointRealtime = endpoint.replaceFirst("http", "ws")
        }
        updated = true
        return this
    }

    /**
     * Set realtime endpoint
     *
     * @param endpoint
     *
     * @return this
     */
    fun setEndpointRealtime(endpoint: String): Client {
        this.endpointRealtime = endpoint
        updated = true
        return this
    }

    /**
     * Add Header
     *
     * @param key
     * @param value
     *
     * @return this
     */
    fun addHeader(
        key: String,
        value: String,
    ): Client {
        headers[key] = value
        updated = true
        return this
    }

    /**
     * Sends a "ping" request to Appwrite to verify connectivity.
     *
     * @return String
     */
    suspend fun ping(): String {
        val apiPath = "/ping"
        val apiHeaders = mutableMapOf("content-type" to "application/json")

        return call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            converter = { it.bodyAsText() }
        )
    }

    /**
     * Send the HTTP request
     *
     * @param method
     * @param path
     * @param headers
     * @param params
     *
     * @return String
     */
    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalSerializationApi::class)
    @Throws(AppwriteException::class, CancellationException::class)
    internal suspend fun <T> call(
        method: HttpMethod,
        path: String,
        headers: Map<String, String> = mapOf(),
        params: Map<String, Any?> = emptyMap(),
        onUpload: ((Progress) -> Unit)? = null,
        onDownload: ((Progress) -> Unit)? = null,
        converter: (suspend (HttpResponse) -> T)? = null,
    ): T {
        createOrGetClient()
        return http.prepareRequest(path.replaceFirst("/", "")) {
            if (onUpload != null) {
                onUpload { sent, total ->
                    onUpload(Progress(total ?: 0L, sent))
                }
            }
            if (onDownload != null) {
                onDownload { received, total ->
                    onDownload(Progress(total ?: 0, received))
                }
            }
            this.method = method
            headers.forEach {
                this.headers.append(it.key, it.value)
            }
            when (method) {
                HttpMethod.Get -> {
                    prepareGetParams(params)
                }

                else -> {
                    if (headers["content-type"] == ContentType.MultiPart.FormData.toString()) {
                        prepareMultiDataFormParams(params)
                    } else {
                        preparePostParams(params)
                    }
                }
            }
        }.execute { response ->
            if (response.status.value in 200..299) {
                converter?.invoke(response) ?: throw AppwriteException(message = "Response converter is null")
            } else {
                try {
                    throw response.body<AppwriteException>()
                } catch (_: Exception) {
                    throw AppwriteException(
                        message = response.bodyAsText(),
                        code = response.status.value,
                        type = response.status.description,
                    )
                }
            }
        }
    }

    private fun HttpRequestBuilder.prepareGetParams(params: Map<String, Any?>) {
        for ((key, value) in params) {
            when (value) {
                is List<*> -> {
                    for (item in value) {
                        parameter("$key[]", item.toString())
                    }
                }

                else -> {
                    parameter(key, value.toString())
                }
            }
        }
    }

    private fun HttpRequestBuilder.prepareMultiDataFormParams(params: Map<String, Any?>) {
        val formData =
            formData {
                for ((key, value) in params) {
                    when (value) {
                        is List<*> -> {
                            for (item in value) {
                                append("$key[]", item.toString())
                            }
                        }

                        is Path -> {
                            append(
                                key,
                                InputProvider(
                                    SystemFileSystem.metadataOrNull(value)?.size,
                                ) {
                                    SystemFileSystem.source(value)
                                        .buffered()
                                },
                                Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        ContentType.Application.OctetStream.toString(),
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${value.name}",
                                    )
                                },
                            )
                        }

                        else -> {
                            append(key, value.toString())
                        }
                    }
                }
            }
        setBody(MultiPartFormDataContent(formData))
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun HttpRequestBuilder.preparePostParams(params: Map<String, Any?>) {
        val bodyMap =
            buildJsonObject {
                for ((key, value) in params) {
                    when (value) {
                        is List<*> -> {
                            putJsonArray(key) {
                                addAll(value.mapNotNull { it?.encodeUnknownValue() })
                            }
                        }

                        is Map<*, *> -> {
                            putJsonObject(key) {
                                value.forEach { (k, v) ->
                                    v?.encodeUnknownValue()?.let { put(k.toString(), it) }
                                }
                            }
                        }

                        else -> {
                            value?.encodeUnknownValue()?.let { put(key, it) }
                        }
                    }
                }
            }
        setBody(bodyMap)
    }

    internal fun createOrGetClient(): HttpClient {
        if (!updated) return http
        http =
            HttpClient(httpEngine()) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
                install(HttpCookies) {
                    storage = CachedCookiesStorage()
                }
                install(Logging) {
                    level = LogLevel.INFO
                }
                install(DefaultRequest) {
                    if (endpoint.endsWith("/")) {
                        url(endpoint)
                    } else {
                        url("$endpoint/")
                    }
                    this@Client.headers.forEach {
                        headers.append(it.key, it.value)
                    }
                }
                install(WebSockets) {
                    pingInterval = 0.seconds
                }
            }
        updated = false
        return http
    }
}

internal expect fun httpEngine(): HttpClientEngine

internal expect fun defaultHeaders(): MutableMap<String, String>

internal expect fun Client.onClientInit()
