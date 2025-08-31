package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rows List
 */
@Serializable
data class RowList<T>(
    /**
     * Total number of rows that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of rows.
     */
    @SerialName("rows")
    val rows: List<T>,
)
