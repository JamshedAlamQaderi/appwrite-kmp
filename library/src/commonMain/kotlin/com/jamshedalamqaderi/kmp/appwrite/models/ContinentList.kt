package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Continents List
 */
@Serializable
data class ContinentList(
    /**
     * Total number of continents documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of continents.
     */
    @SerialName("continents")
    val continents: List<Continent>,
)
