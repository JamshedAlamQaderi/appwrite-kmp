package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Phones List
 */
@Serializable
data class PhoneList(
    /**
     * Total number of phones documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of phones.
     */
    @SerialName("phones")
    val phones: List<Phone>,
)
