package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.extensions.json
import com.jamshedalamqaderi.kmp.appwrite.models.ClientParam
import com.jamshedalamqaderi.kmp.appwrite.models.Document
import com.jamshedalamqaderi.kmp.appwrite.models.DocumentList
import com.jamshedalamqaderi.kmp.appwrite.models.asDocument
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmOverloads

/**
 * The Databases service allows you to create structured collections of documents, query and filter lists of documents
 **/
class Databases(client: Client) : Service(client) {
    /**
     * List documents
     *
     * Get a list of all the user&#039;s documents in a given collection. You can use the query params to filter your results.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID. You can create a new collection using the Database service [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection).
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [io.appwrite.models.DocumentList<T>]
     */
    @JvmOverloads
    suspend fun <T> listDocuments(
        databaseId: String,
        collectionId: String,
        queries: List<String>? = null,
        nestedType: KSerializer<T>,
    ): DocumentList<Document<T>> {
        val apiPath =
            "/databases/{databaseId}/collections/{collectionId}/documents"
                .replace("{databaseId}", databaseId)
                .replace("{collectionId}", collectionId)

        val apiParams =
            listOf(
                ClientParam.ListParam("queries", queries ?: emptyList()),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        val documents =
            client.call(
                HttpMethod.Get,
                apiPath,
                DocumentList.serializer(JsonElement.serializer()),
                apiHeaders,
                apiParams,
            )
        return DocumentList(
            total = documents.total,
            documents = documents.documents.map { it.asDocument(nestedType) },
        )
    }

    /**
     * List documents
     *
     * Get a list of all the user&#039;s documents in a given collection. You can use the query params to filter your results.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID. You can create a new collection using the Database service [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection).
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [io.appwrite.models.DocumentList<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun listDocuments(
        databaseId: String,
        collectionId: String,
        queries: List<String>? = null,
    ): DocumentList<Document<JsonElement>> =
        listDocuments(
            databaseId,
            collectionId,
            queries,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Create document
     *
     * Create a new Document. Before using this route, you should create a new collection resource using either a [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection) API or directly from your database console.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID. You can create a new collection using the Database service [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection). Make sure to define attributes before creating documents.
     * @param documentId Document ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param data Document data as JSON object.
     * @param permissions An array of permissions strings. By default, only the current user is granted all permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [io.appwrite.models.Document<T>]
     */
    @JvmOverloads
    suspend fun <T> createDocument(
        databaseId: String,
        collectionId: String,
        documentId: String,
        data: T,
        permissions: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Document<T> {
        val apiPath =
            "/databases/{databaseId}/collections/{collectionId}/documents"
                .replace("{databaseId}", databaseId)
                .replace("{collectionId}", collectionId)

        val apiParams =
            listOf(
                ClientParam.StringParam("documentId", documentId),
                ClientParam.StringParam("data", json.encodeToString(nestedType, data)),
                ClientParam.ListParam("permissions", permissions ?: emptyList()),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams,
        ).asDocument(nestedType)
    }

    /**
     * Create document
     *
     * Create a new Document. Before using this route, you should create a new collection resource using either a [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection) API or directly from your database console.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID. You can create a new collection using the Database service [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection). Make sure to define attributes before creating documents.
     * @param documentId Document ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param data Document data as JSON object.
     * @param permissions An array of permissions strings. By default, only the current user is granted all permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [io.appwrite.models.Document<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun createDocument(
        databaseId: String,
        collectionId: String,
        documentId: String,
        data: JsonElement,
        permissions: List<String>? = null,
    ): Document<JsonElement> =
        createDocument(
            databaseId,
            collectionId,
            documentId,
            data,
            permissions,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Get document
     *
     * Get a document by its unique ID. This endpoint response returns a JSON object with the document data.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID. You can create a new collection using the Database service [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection).
     * @param documentId Document ID.
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [io.appwrite.models.Document<T>]
     */
    @JvmOverloads
    suspend fun <T> getDocument(
        databaseId: String,
        collectionId: String,
        documentId: String,
        queries: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Document<T> {
        val apiPath =
            "/databases/{databaseId}/collections/{collectionId}/documents/{documentId}"
                .replace("{databaseId}", databaseId)
                .replace("{collectionId}", collectionId)
                .replace("{documentId}", documentId)

        val apiParams =
            listOf(
                ClientParam.ListParam("queries", queries ?: emptyList()),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams,
        ).asDocument(nestedType)
    }

    /**
     * Get document
     *
     * Get a document by its unique ID. This endpoint response returns a JSON object with the document data.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID. You can create a new collection using the Database service [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection).
     * @param documentId Document ID.
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [io.appwrite.models.Document<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun getDocument(
        databaseId: String,
        collectionId: String,
        documentId: String,
        queries: List<String>? = null,
    ): Document<JsonElement> =
        getDocument(
            databaseId,
            collectionId,
            documentId,
            queries,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Update document
     *
     * Update a document by its unique ID. Using the patch method you can pass only specific fields that will get updated.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID.
     * @param documentId Document ID.
     * @param data Document data as JSON object. Include only attribute and value pairs to be updated.
     * @param permissions An array of permissions strings. By default, the current permissions are inherited. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [io.appwrite.models.Document<T>]
     */
    @JvmOverloads
    suspend fun <T> updateDocument(
        databaseId: String,
        collectionId: String,
        documentId: String,
        data: T,
        permissions: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Document<T> {
        val apiPath =
            "/databases/{databaseId}/collections/{collectionId}/documents/{documentId}"
                .replace("{databaseId}", databaseId)
                .replace("{collectionId}", collectionId)
                .replace("{documentId}", documentId)

        val apiParams =
            listOf(
                ClientParam.StringParam("data", json.encodeToString(nestedType, data)),
                ClientParam.ListParam("permissions", permissions ?: emptyList()),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams,
        ).asDocument(nestedType)
    }

    /**
     * Update document
     *
     * Update a document by its unique ID. Using the patch method you can pass only specific fields that will get updated.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID.
     * @param documentId Document ID.
     * @param data Document data as JSON object. Include only attribute and value pairs to be updated.
     * @param permissions An array of permissions strings. By default, the current permissions are inherited. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [io.appwrite.models.Document<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updateDocument(
        databaseId: String,
        collectionId: String,
        documentId: String,
        data: JsonElement,
        permissions: List<String>? = null,
    ): Document<JsonElement> =
        updateDocument(
            databaseId,
            collectionId,
            documentId,
            data,
            permissions,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Delete document
     *
     * Delete a document by its unique ID.
     *
     * @param databaseId Database ID.
     * @param collectionId Collection ID. You can create a new collection using the Database service [server integration](https://appwrite.io/docs/server/databases#databasesCreateCollection).
     * @param documentId Document ID.
     * @return [Any]
     */
    suspend fun deleteDocument(
        databaseId: String,
        collectionId: String,
        documentId: String,
    ) {
        val apiPath =
            "/databases/{databaseId}/collections/{collectionId}/documents/{documentId}"
                .replace("{databaseId}", databaseId)
                .replace("{collectionId}", collectionId)
                .replace("{documentId}", documentId)

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Delete,
            apiPath,
            Unit.serializer(),
            apiHeaders,
            emptyList(),
        )
    }
}
