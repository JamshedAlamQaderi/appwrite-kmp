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
 * Row
 */
@OptIn(ExperimentalTime::class)
@Serializable
data class Row<T>(
    /**
     * Row ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Row automatically incrementing ID.
     */
    @SerialName("\$sequence")
    val sequence: Long,
    /**
     * Table ID.
     */
    @SerialName("\$tableId")
    val tableId: String,
    /**
     * Database ID.
     */
    @SerialName("\$databaseId")
    val databaseId: String,
    /**
     * Row creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: Instant,
    /**
     * Row update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: Instant,
    /**
     * Row permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
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
internal fun <T> JsonElement.asRow(deserializer: DeserializationStrategy<T>): Row<T> {
    val keys =
        listOf(
            "\$id",
            "\$sequence",
            "\$tableId",
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
    return Row(
        id = getString("\$id"),
        sequence = getString("\$sequence").toLong(),
        tableId = getString("\$collectionId"),
        databaseId = getString("\$databaseId"),
        createdAt = Instant.parse(getString("\$createdAt")),
        updatedAt = Instant.parse(getString("\$updatedAt")),
        permissions = getStringList("\$permissions"),
        data = dataObject.jsonCast(JsonElement.serializer(), deserializer),
    )
}
