package com.jamshedalamqaderi.kmp.appwrite.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Membership
 */
@OptIn(ExperimentalTime::class)
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
    val createdAt: Instant,
    /**
     * Membership update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: Instant,
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
    val joined: Instant,
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
