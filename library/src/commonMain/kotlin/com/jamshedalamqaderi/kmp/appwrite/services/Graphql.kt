package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.models.ClientParam
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.JsonElement

/**
 * The GraphQL API allows you to query and mutate your Appwrite server using GraphQL.
 **/
class Graphql(client: Client) : Service(client) {
    /**
     * GraphQL endpoint
     *
     * Execute a GraphQL mutation.
     *
     * @param query The query or queries to execute.
     * @return [JsonElement]
     */
    suspend fun query(query: String): JsonElement {
        val apiPath = "/graphql"

        val apiParams =
            listOf(
                ClientParam.StringParam("query", query),
            )
        val apiHeaders =
            mutableMapOf(
                "x-sdk-graphql" to "true",
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Post,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams,
        )
    }

    /**
     * GraphQL endpoint
     *
     * Execute a GraphQL mutation.
     *
     * @param query The query or queries to execute.
     * @return [JsonElement]
     */
    suspend fun mutation(query: String): JsonElement {
        val apiPath = "/graphql/mutation"

        val apiParams =
            listOf(
                ClientParam.StringParam("query", query),
            )
        val apiHeaders =
            mutableMapOf(
                "x-sdk-graphql" to "true",
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
            apiParams,
        )
    }
}
