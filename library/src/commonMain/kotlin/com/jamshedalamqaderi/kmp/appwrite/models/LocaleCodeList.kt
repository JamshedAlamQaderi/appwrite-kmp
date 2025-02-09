package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Locale codes list
 */
@Serializable
data class LocaleCodeList(
    /**
     * Total number of localeCodes documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of localeCodes.
     */
    @SerialName("localeCodes")
    val localeCodes: List<LocaleCode>,
)
