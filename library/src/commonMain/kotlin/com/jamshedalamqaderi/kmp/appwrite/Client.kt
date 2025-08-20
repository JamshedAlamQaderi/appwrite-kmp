package com.jamshedalamqaderi.kmp.appwrite

import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.extensions.fromJson
import com.jamshedalamqaderi.kmp.appwrite.extensions.json
import com.jamshedalamqaderi.kmp.appwrite.models.ClientParam
import com.jamshedalamqaderi.kmp.appwrite.models.Progress
import com.jamshedalamqaderi.kmp.appwrite.stores.CachedCookiesStorage
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.io.buffered
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.seconds

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
    suspend fun <T> call(
        method: HttpMethod,
        path: String,
        deserializer: DeserializationStrategy<T>,
        headers: Map<String, String> = mapOf(),
        params: List<ClientParam> = emptyList(),
        onUpload: ((Progress) -> Unit)? = null,
        onDownload: ((Progress) -> Unit)? = null,
    ): T {
        createOrGetClient()
        val response =
            http.request(path.replaceFirst("/", "")) {
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
                        params.forEach { param ->
                            when (param) {
                                is ClientParam.FileParam -> {}
                                is ClientParam.ListParam -> {
                                    param.value.forEach { paramValue ->
                                        parameter("${param.key}[]", paramValue)
                                    }
                                }

                                is ClientParam.StringParam -> {
                                    parameter(param.key, param.value)
                                }

                                is ClientParam.MapParam -> {}
                            }
                        }
                    }

                    else -> {
                        if (headers["content-type"] == ContentType.MultiPart.FormData.toString()) {
                            val formDataContent =
                                formData {
                                    params.forEach { param ->
                                        when (param) {
                                            is ClientParam.FileParam -> {
                                                append(
                                                    "file",
                                                    InputProvider(
                                                        SystemFileSystem.metadataOrNull(param.path)?.size,
                                                    ) {
                                                        SystemFileSystem.source(param.path)
                                                            .buffered()
                                                    },
                                                    Headers.build {
                                                        append(
                                                            HttpHeaders.ContentType,
                                                            ContentType.Application.OctetStream.toString(),
                                                        )
                                                        append(
                                                            HttpHeaders.ContentDisposition,
                                                            "filename=${param.path.name}",
                                                        )
                                                    },
                                                )
                                            }

                                            is ClientParam.ListParam -> {
                                                param.value.forEach { paramValue ->
                                                    append("${param.key}[]", paramValue)
                                                }
                                            }

                                            is ClientParam.StringParam -> {
                                                append(param.key, param.value.toString())
                                            }

                                            is ClientParam.MapParam -> {}
                                        }
                                    }
                                }
                            setBody(MultiPartFormDataContent(formDataContent))
                        } else {
                            val bodyMap =
                                buildJsonObject {
                                    params.forEach { param ->
                                        when (param) {
                                            is ClientParam.FileParam -> {}
                                            is ClientParam.ListParam -> {
                                                putJsonArray(param.key) {
                                                    addAll(param.value)
                                                }
                                            }

                                            is ClientParam.StringParam -> {
                                                put(param.key, param.value)
                                            }

                                            is ClientParam.MapParam -> {
                                                putJsonObject(param.key) {
                                                    param.value.forEach { entry ->
                                                        put(entry.key, entry.value)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            setBody(json.encodeToString(bodyMap))
                        }
                    }
                }
            }.bodyAsText()
        return try {
            if (deserializer == Unit.serializer()) {
                Unit as T
            } else {
                response.fromJson(deserializer)
            }
        } catch (_: Exception) {
            throw response.fromJson<AppwriteException>()
        }
    }

    internal fun createOrGetClient(): HttpClient {
        if (!updated) return http
        http =
            HttpClient(httpEngine()) {
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
