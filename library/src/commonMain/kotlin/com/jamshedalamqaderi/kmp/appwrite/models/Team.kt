package com.jamshedalamqaderi.kmp.appwrite.models

import com.jamshedalamqaderi.kmp.appwrite.extensions.jsonCast
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

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
    val prefs: T,
)

internal fun <T> Team<JsonElement>.asTeamPreferences(deserializer: DeserializationStrategy<T>): Team<Preferences<T>> {
    return Team(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
        name = name,
        total = total,
        prefs =
            Preferences(
                data = this.prefs.jsonCast(JsonElement.serializer(), deserializer),
            ),
    )
}
