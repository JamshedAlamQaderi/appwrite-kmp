package com.jamshedalamqaderi.kmp.appwrite.models

import com.jamshedalamqaderi.kmp.appwrite.extensions.getString
import com.jamshedalamqaderi.kmp.appwrite.extensions.getStringList
import com.jamshedalamqaderi.kmp.appwrite.extensions.jsonCast
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject

/**
 * Document
 */
@OptIn(ExperimentalTime::class)
@Deprecated("Use TableDB instead.", replaceWith = ReplaceWith("Row"))
@Serializable
data class Document<T>(
    /**
     * Document ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Collection ID.
     */
    @SerialName("\$collectionId")
    val collectionId: String,
    /**
     * Database ID.
     */
    @SerialName("\$databaseId")
    val databaseId: String,
    /**
     * Document creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: Instant,
    /**
     * Document update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: Instant,
    /**
     * Document permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
     */
    @SerialName("\$permissions")
    val permissions: List<String>,
    /**
     * Additional properties
     */
    @SerialName("data")
    val data: T,
)

@OptIn(ExperimentalTime::class)
@Deprecated("Use TableDB instead.", replaceWith = ReplaceWith("asRow"))
internal fun <T> JsonElement.asDocument(deserializer: DeserializationStrategy<T>): Document<T> {
    val keys =
        listOf(
            "\$id",
            "\$collectionId",
            "\$databaseId",
            "\$createdAt",
            "\$updatedAt",
            "\$permissions",
        )
    val dataObject =
        buildJsonObject {
            jsonObject.entries.forEach {
                if (!keys.contains(it.key)) {
                    put(it.key, it.value)
                }
            }
        }
    return Document(
        id = getString("\$id"),
        collectionId = getString("\$collectionId"),
        databaseId = getString("\$databaseId"),
        createdAt = Instant.parse(getString("\$createdAt")),
        updatedAt = Instant.parse(getString("\$updatedAt")),
        permissions = getStringList("\$permissions"),
        data = dataObject.jsonCast(JsonElement.serializer(), deserializer),
    )
}
