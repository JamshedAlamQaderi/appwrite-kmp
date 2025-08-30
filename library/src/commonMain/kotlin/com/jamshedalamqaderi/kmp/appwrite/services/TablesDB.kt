package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.extensions.json
import com.jamshedalamqaderi.kmp.appwrite.models.ClientParam
import com.jamshedalamqaderi.kmp.appwrite.models.Row
import com.jamshedalamqaderi.kmp.appwrite.models.RowList
import com.jamshedalamqaderi.kmp.appwrite.models.asRow
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlin.jvm.JvmOverloads

/**
 *
 **/
class TablesDB(client: Client) : Service(client) {
    /**
     * Get a list of all the user's rows in a given table. You can use the query params to filter your results.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID. You can create a new table using the TableDB service [server integration](https://appwrite.io/docs/server/tablesdbdb#tablesdbCreate).
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.RowList<T>]
     */
    @JvmOverloads
    suspend fun <T> listRows(
        databaseId: String,
        tableId: String,
        queries: List<String>? = null,
        nestedType: KSerializer<T>,
    ): RowList<Row<T>> {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)

        val apiParams =
            listOf(
                ClientParam.ListParam("queries", queries ?: emptyList()),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        val rows =
            client.call(
                HttpMethod.Get,
                apiPath,
                RowList.serializer(JsonElement.serializer()),
                apiHeaders,
                apiParams,
        )
        return RowList(
            total = rows.total,
            rows = rows.rows.map { it.asRow(nestedType) },
        )
    }

    /**
     * Get a list of all the user's rows in a given table. You can use the query params to filter your results.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID. You can create a new table using the TableDB service [server integration](https://appwrite.io/docs/server/tablesdbdb#tablesdbCreate).
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.RowList<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class)
    suspend fun listRows(
        databaseId: String,
        tableId: String,
        queries: List<String>? = null,
    ): RowList<Row<JsonElement>> =
        listRows(
            databaseId,
            tableId,
            queries,
            nestedType = JsonElement.serializer(),
            )

    /**
     * Create a new Row. Before using this route, you should create a new table resource using either a [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreateTable) API or directly from your database console.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID. You can create a new table using the Database service [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreate). Make sure to define columns before creating rows.
     * @param rowId Row ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param data Row data as JSON object.
     * @param permissions An array of permissions strings. By default, only the current user is granted all permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    suspend fun <T> createRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        data: T,
        permissions: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Row<T> {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)

        val apiParams =
            listOf(
                ClientParam.StringParam("rowId", rowId),
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
        ).asRow(nestedType)
    }

    /**
     * Create a new Row. Before using this route, you should create a new table resource using either a [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreateTable) API or directly from your database console.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID. You can create a new table using the Database service [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreate). Make sure to define columns before creating rows.
     * @param rowId Row ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param data Row data as JSON object.
     * @param permissions An array of permissions strings. By default, only the current user is granted all permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class)
    suspend fun createRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        data: JsonElement,
        permissions: List<String>? = null,
    ): Row<JsonElement> =
        createRow(
            databaseId,
            tableId,
            rowId,
            data,
            permissions,
            nestedType = JsonElement.serializer(),
    )

    /**
     * Get a row by its unique ID. This endpoint response returns a JSON object with the row data.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID. You can create a new table using the Database service [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreate).
     * @param rowId Row ID.
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    suspend fun <T> getRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        queries: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Row<T> {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows/{rowId}"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)
            .replace("{rowId}", rowId)

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
        ).asRow(nestedType)
    }

    /**
     * Get a row by its unique ID. This endpoint response returns a JSON object with the row data.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID. You can create a new table using the Database service [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreate).
     * @param rowId Row ID.
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class)
    suspend fun getRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        queries: List<String>? = null,
    ): Row<JsonElement> =
        getRow(
            databaseId,
            tableId,
            rowId,
            queries,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Create or update a Row. Before using this route, you should create a new table resource using either a [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreateTable) API or directly from your database console.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param data Row data as JSON object. Include all required columns of the row to be created or updated.
     * @param permissions An array of permissions strings. By default, the current permissions are inherited. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    suspend fun <T> upsertRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        data: T,
        permissions: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Row<T> {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows/{rowId}"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)
            .replace("{rowId}", rowId)

        val apiParams =
            listOf(
                ClientParam.StringParam("data", json.encodeToString(nestedType, data)),
                ClientParam.ListParam("permissions", permissions ?: emptyList()),
            )
        val apiHeaders = mutableMapOf(
            "content-type" to "application/json",
        )

        return client.call(
            HttpMethod.Put,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams
        ).asRow(nestedType)
    }

    /**
     * Create or update a Row. Before using this route, you should create a new table resource using either a [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreateTable) API or directly from your database console.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param data Row data as JSON object. Include all required columns of the row to be created or updated.
     * @param permissions An array of permissions strings. By default, the current permissions are inherited. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class)
    suspend fun upsertRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        data: JsonElement,
        permissions: List<String>? = null,
    ): Row<JsonElement> = upsertRow(
        databaseId,
        tableId,
        rowId,
        data,
        permissions,
        nestedType = JsonElement.serializer(),
    )

    /**
     * Update a row by its unique ID. Using the patch method you can pass only specific fields that will get updated.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param data Row data as JSON object. Include only columns and value pairs to be updated.
     * @param permissions An array of permissions strings. By default, the current permissions are inherited. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    suspend fun <T> updateRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        data: T,
        permissions: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Row<T> {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows/{rowId}"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)
            .replace("{rowId}", rowId)

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
        ).asRow(nestedType)
    }

    /**
     * Update a row by its unique ID. Using the patch method you can pass only specific fields that will get updated.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param data Row data as JSON object. Include only columns and value pairs to be updated.
     * @param permissions An array of permissions strings. By default, the current permissions are inherited. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class)
    suspend fun updateRow(
        databaseId: String,
        tableId: String,
        rowId: String,
        data: JsonElement,
        permissions: List<String>? = null,
    ): Row<JsonElement> =
        updateRow(
            databaseId,
            tableId,
            rowId,
            data,
            permissions,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Delete a row by its unique ID.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID. You can create a new table using the Database service [server integration](https://appwrite.io/docs/server/tablesdb#tablesDBCreate).
     * @param rowId Row ID.
     * @return [Any]
     */
    suspend fun deleteRow(
        databaseId: String,
        tableId: String,
        rowId: String,
    ): Any {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows/{rowId}"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)
            .replace("{rowId}", rowId)

        val apiHeaders = mutableMapOf(
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


    /**
     * Decrement a specific column of a row by a given value.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param column Column key.
     * @param value Value to increment the column by. The value must be a number.
     * @param min Minimum value for the column. If the current value is lesser than this value, an exception will be thrown.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    suspend fun <T> decrementRowColumn(
        databaseId: String,
        tableId: String,
        rowId: String,
        column: String,
        value: Double? = null,
        min: Double? = null,
        nestedType: KSerializer<T>,
    ): Row<T> {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows/{rowId}/{column}/decrement"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)
            .replace("{rowId}", rowId)
            .replace("{column}", column)

        val apiParams =
            listOf(
                ClientParam.StringParam("value", value.toString()),
                ClientParam.StringParam("min", min.toString()),
            )
        val apiHeaders = mutableMapOf(
            "content-type" to "application/json",
        )
        return client.call(
            HttpMethod.Patch,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams
        ).asRow(nestedType)
    }

    /**
     * Decrement a specific column of a row by a given value.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param column Column key.
     * @param value Value to increment the column by. The value must be a number.
     * @param min Minimum value for the column. If the current value is lesser than this value, an exception will be thrown.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class)
    suspend fun decrementRowColumn(
        databaseId: String,
        tableId: String,
        rowId: String,
        column: String,
        value: Double? = null,
        min: Double? = null,
    ): Row<JsonElement> = decrementRowColumn(
        databaseId,
        tableId,
        rowId,
        column,
        value,
        min,
        nestedType = JsonElement.serializer(),
    )

    /**
     * Increment a specific column of a row by a given value.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param column Column key.
     * @param value Value to increment the column by. The value must be a number.
     * @param max Maximum value for the column. If the current value is greater than this value, an error will be thrown.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    suspend fun <T> incrementRowColumn(
        databaseId: String,
        tableId: String,
        rowId: String,
        column: String,
        value: Double? = null,
        max: Double? = null,
        nestedType: KSerializer<T>,
    ): Row<T> {
        val apiPath = "/tablesdb/{databaseId}/tables/{tableId}/rows/{rowId}/{column}/increment"
            .replace("{databaseId}", databaseId)
            .replace("{tableId}", tableId)
            .replace("{rowId}", rowId)
            .replace("{column}", column)

        val apiParams =
            listOf(
                ClientParam.StringParam("value", value.toString()),
                ClientParam.StringParam("max", max.toString()),
            )
        val apiHeaders = mutableMapOf(
            "content-type" to "application/json",
        )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams,
        ).asRow(nestedType)
    }

    /**
     * Increment a specific column of a row by a given value.
     *
     * @param databaseId Database ID.
     * @param tableId Table ID.
     * @param rowId Row ID.
     * @param column Column key.
     * @param value Value to increment the column by. The value must be a number.
     * @param max Maximum value for the column. If the current value is greater than this value, an error will be thrown.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Row<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class)
    suspend fun incrementRowColumn(
        databaseId: String,
        tableId: String,
        rowId: String,
        column: String,
        value: Double? = null,
        max: Double? = null,
    ): Row<JsonElement> = incrementRowColumn(
        databaseId,
        tableId,
        rowId,
        column,
        value,
        max,
        nestedType = JsonElement.serializer(),
    )

}