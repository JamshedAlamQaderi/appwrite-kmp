package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Countries List
 */
@Serializable
data class CountryList(
    /**
     * Total number of countries documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of countries.
     */
    @SerialName("countries")
    val countries: List<Country>,
)
