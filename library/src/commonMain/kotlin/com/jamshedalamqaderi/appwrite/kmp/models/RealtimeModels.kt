package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

data class RealtimeSubscription(
    private val close: () -> Unit,
) {
    fun close() = close.invoke()
}

@Serializable
data class RealtimeCallback<T>(
    val channels: Collection<String>,
    val payloadClass: KSerializer<T>,
    val callback: (RealtimeResponseEvent<T>) -> Unit,
)

@Serializable
open class RealtimeResponse<T>(
    val type: String,
    val data: T,
)

@Serializable
data class RealtimeResponseEvent<T>(
    val events: Collection<String>,
    val channels: Collection<String>,
    val timestamp: String,
    var payload: T,
)

enum class RealtimeCode(val value: Int) {
    POLICY_VIOLATION(1008),
    UNKNOWN_ERROR(-1),
}
