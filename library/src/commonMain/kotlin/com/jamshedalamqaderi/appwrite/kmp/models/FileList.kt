package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Files List
 */
@Serializable
data class FileList(
    /**
     * Total number of files documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of files.
     */
    @SerialName("files")
    val files: List<File>,
)
