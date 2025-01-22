package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Logs List
 */
@Serializable
data class LogList(
    /**
     * Total number of logs documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of logs.
     */
    @SerialName("logs")
    val logs: List<Log>,
)
