package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Currencies List
 */
@Serializable
data class CurrencyList(
    /**
     * Total number of currencies documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of currencies.
     */
    @SerialName("currencies")
    val currencies: List<Currency>,
)
