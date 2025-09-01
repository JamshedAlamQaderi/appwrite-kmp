package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

internal typealias RealtimeCallbackFunction<T> = suspend (RealtimeResponseEvent<T>) -> Unit

data class RealtimeSubscription(
    private val close: () -> Unit,
) {
    fun close() = close.invoke()
}

@Serializable
data class RealtimeCallback(
    val channels: Set<String>,
    val callback: RealtimeCallbackFunction<JsonElement>,
)

@Serializable
open class RealtimeResponse<T>(
    val type: String,
    val data: T? = null,
)

@Serializable
data class RealtimeResponseEvent<T>(
    val events: List<String>,
    val channels: List<String>,
    val timestamp: String,
    var payload: T,
)

enum class RealtimeCode(
    val value: Int,
) {
    POLICY_VIOLATION(1008),
    UNKNOWN_ERROR(-1),
}
