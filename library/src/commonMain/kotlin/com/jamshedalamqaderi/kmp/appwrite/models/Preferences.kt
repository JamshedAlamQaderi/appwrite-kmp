package com.jamshedalamqaderi.kmp.appwrite.models

import com.jamshedalamqaderi.kmp.appwrite.extensions.jsonCast
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Preferences
 */
@Serializable
data class Preferences<T>(
    /**
     * Additional properties
     */
    @SerialName("data")
    val data: T? = null,
)

fun <T> JsonElement.asPreferences(deserializer: DeserializationStrategy<T>): Preferences<T> =
    Preferences(
        data = this.jsonCast(JsonElement.serializer(), deserializer),
    )
