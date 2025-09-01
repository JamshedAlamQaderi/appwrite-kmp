package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * MFA Challenge
 */
@OptIn(ExperimentalTime::class)
@Serializable
data class MfaChallenge(
    /**
     * Token ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Token creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: Instant,
    /**
     * User ID.
     */
    @SerialName("userId")
    val userId: String,
    /**
     * Token expiration date in ISO 8601 format.
     */
    @SerialName("expire")
    val expire: String,
)
