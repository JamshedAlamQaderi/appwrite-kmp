package com.jamshedalamqaderi.kmp.appwrite.models

import com.jamshedalamqaderi.kmp.appwrite.extensions.getString
import com.jamshedalamqaderi.kmp.appwrite.extensions.getStringList
import com.jamshedalamqaderi.kmp.appwrite.extensions.jsonCast
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject

/**
 * Document
 */
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
    val createdAt: String,
    /**
     * Document update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,
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

fun <T> JsonElement.asDocument(deserializer: DeserializationStrategy<T>): Document<T> {
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
        createdAt = getString("\$createdAt"),
        updatedAt = getString("\$updatedAt"),
        permissions = getStringList("\$permissions"),
        data = dataObject.jsonCast(JsonElement.serializer(), deserializer),
    )
}
