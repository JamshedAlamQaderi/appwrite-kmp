package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Target
 */
@OptIn(ExperimentalTime::class)
@Serializable
data class Target(
    /**
     * Target ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Target creation time in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: Instant,
    /**
     * Target update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: Instant,
    /**
     * Target Name.
     */
    @SerialName("name")
    val name: String,
    /**
     * User ID.
     */
    @SerialName("userId")
    val userId: String,
    /**
     * Provider ID.
     */
    @SerialName("providerId")
    var providerId: String?,
    /**
     * The target provider type. Can be one of the following: `email`, `sms` or `push`.
     */
    @SerialName("providerType")
    val providerType: String,
    /**
     * The target identifier.
     */
    @SerialName("identifier")
    val identifier: String,
)
