package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Identity
 */
@Serializable
data class Identity(
    /**
     * Identity ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Identity creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,
    /**
     * Identity update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,
    /**
     * User ID.
     */
    @SerialName("userId")
    val userId: String,
    /**
     * Identity Provider.
     */
    @SerialName("provider")
    val provider: String,
    /**
     * ID of the User in the Identity Provider.
     */
    @SerialName("providerUid")
    val providerUid: String,
    /**
     * Email of the User in the Identity Provider.
     */
    @SerialName("providerEmail")
    val providerEmail: String,
    /**
     * Identity Provider Access Token.
     */
    @SerialName("providerAccessToken")
    val providerAccessToken: String,
    /**
     * The date of when the access token expires in ISO 8601 format.
     */
    @SerialName("providerAccessTokenExpiry")
    val providerAccessTokenExpiry: String,
    /**
     * Identity Provider Refresh Token.
     */
    @SerialName("providerRefreshToken")
    val providerRefreshToken: String,
)
