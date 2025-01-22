package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * MFA Challenge
 */
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
    val createdAt: String,
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
