package com.jamshedalamqaderi.kmp.appwrite.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Token
 */
@OptIn(ExperimentalTime::class)
@Serializable
data class Token(
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
     * Token secret key. This will return an empty string unless the response is returned using an API key or as part of a webhook payload.
     */
    @SerialName("secret")
    val secret: String,
    /**
     * Token expiration date in ISO 8601 format.
     */
    @SerialName("expire")
    val expire: String,
    /**
     * Security phrase of a token. Empty if security phrase was not requested when creating a token. It includes randomly generated phrase which is also sent in the external resource such as email.
     */
    @SerialName("phrase")
    val phrase: String,
)
