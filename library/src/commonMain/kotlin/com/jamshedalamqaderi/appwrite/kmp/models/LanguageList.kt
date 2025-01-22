package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Languages List
 */
@Serializable
data class LanguageList(
    /**
     * Total number of languages documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of languages.
     */
    @SerialName("languages")
    val languages: List<Language>,
)
