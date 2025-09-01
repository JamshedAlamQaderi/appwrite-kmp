package com.jamshedalamqaderi.kmp.appwrite.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * File
 */
@OptIn(ExperimentalTime::class)
@Serializable
data class File(
    /**
     * File ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Bucket ID.
     */
    @SerialName("bucketId")
    val bucketId: String,
    /**
     * File creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: Instant,
    /**
     * File update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: Instant,
    /**
     * File permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
     */
    @SerialName("\$permissions")
    val permissions: List<String>,
    /**
     * File name.
     */
    @SerialName("name")
    val name: String,
    /**
     * File MD5 signature.
     */
    @SerialName("signature")
    val signature: String,
    /**
     * File mime type.
     */
    @SerialName("mimeType")
    val mimeType: String,
    /**
     * File original size in bytes.
     */
    @SerialName("sizeOriginal")
    val sizeOriginal: Long,
    /**
     * Total number of chunks available
     */
    @SerialName("chunksTotal")
    val chunksTotal: Long,
    /**
     * Total number of chunks uploaded
     */
    @SerialName("chunksUploaded")
    val chunksUploaded: Long,
)
