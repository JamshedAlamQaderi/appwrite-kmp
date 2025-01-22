package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Membership
 */
@Serializable
data class Membership(
    /**
     * Membership ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Membership creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,
    /**
     * Membership update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,
    /**
     * User ID.
     */
    @SerialName("userId")
    val userId: String,
    /**
     * User name.
     */
    @SerialName("userName")
    val userName: String,
    /**
     * User email address.
     */
    @SerialName("userEmail")
    val userEmail: String,
    /**
     * Team ID.
     */
    @SerialName("teamId")
    val teamId: String,
    /**
     * Team name.
     */
    @SerialName("teamName")
    val teamName: String,
    /**
     * Date, the user has been invited to join the team in ISO 8601 format.
     */
    @SerialName("invited")
    val invited: String,
    /**
     * Date, the user has accepted the invitation to join the team in ISO 8601 format.
     */
    @SerialName("joined")
    val joined: String,
    /**
     * User confirmation status, true if the user has joined the team or false otherwise.
     */
    @SerialName("confirm")
    val confirm: Boolean,
    /**
     * Multi factor authentication status, true if the user has MFA enabled or false otherwise.
     */
    @SerialName("mfa")
    val mfa: Boolean,
    /**
     * User list of roles
     */
    @SerialName("roles")
    val roles: List<String>,
)
