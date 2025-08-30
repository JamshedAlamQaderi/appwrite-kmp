package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Documents List
 */
@Deprecated("Use TableDB instead.", replaceWith = ReplaceWith("RowList"))
@Serializable
data class DocumentList<T>(
    /**
     * Total number of documents documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of documents.
     */
    @SerialName("documents")
    val documents: List<T>,
)
