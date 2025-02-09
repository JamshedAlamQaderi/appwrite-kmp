package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Team
 */
@Serializable
data class Team<T>(
    /**
     * Team ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Team creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,
    /**
     * Team update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,
    /**
     * Team name.
     */
    @SerialName("name")
    val name: String,
    /**
     * Total number of team members.
     */
    @SerialName("total")
    val total: Long,
    /**
     * Team preferences as a key-value object
     */
    @SerialName("prefs")
    val prefs: Preferences<T>,
)
