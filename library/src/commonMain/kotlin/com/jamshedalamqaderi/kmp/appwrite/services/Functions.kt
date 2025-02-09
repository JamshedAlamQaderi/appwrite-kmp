package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.enums.ExecutionMethod
import com.jamshedalamqaderi.kmp.appwrite.models.ClientParam
import com.jamshedalamqaderi.kmp.appwrite.models.Execution
import com.jamshedalamqaderi.kmp.appwrite.models.ExecutionList
import io.ktor.http.HttpMethod
import kotlin.jvm.JvmOverloads

/**
 * The Functions Service allows you view, create and manage your Cloud Functions.
 **/
class Functions(client: Client) : Service(client) {
    /**
     * List executions
     *
     * Get a list of all the current user function execution logs. You can use the query params to filter your results.
     *
     * @param functionId Function ID.
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long. You may filter on the following attributes: trigger, status, responseStatusCode, duration, requestMethod, requestPath, deploymentId
     * @param search Search term to filter your list results. Max length: 256 chars.
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.ExecutionList]
     */
    @JvmOverloads
    suspend fun listExecutions(
        functionId: String,
        queries: List<String>? = null,
        search: String? = null,
    ): ExecutionList {
        val apiPath =
            "/functions/{functionId}/executions"
                .replace("{functionId}", functionId)

        val apiParams =
            listOf(
                ClientParam.ListParam("queries", queries ?: emptyList()),
                ClientParam.StringParam("search", search),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            ExecutionList.serializer(),
            apiHeaders,
            apiParams,
        )
    }

    /**
     * Create execution
     *
     * Trigger a function execution. The returned object will return you the current execution status. You can ping the `Get Execution` endpoint to get updates on the current execution status. Once this endpoint is called, your function execution process will start asynchronously.
     *
     * @param functionId Function ID.
     * @param body HTTP body of execution. Default value is empty string.
     * @param async Execute code in the background. Default value is false.
     * @param path HTTP path of execution. Path can include query params. Default value is /
     * @param method HTTP method of execution. Default value is GET.
     * @param headers HTTP headers of execution. Defaults to empty.
     * @param scheduledAt Scheduled execution time in [ISO 8601](https://www.iso.org/iso-8601-date-and-time-format.html) format. DateTime value must be in future with precision in minutes.
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.Execution]
     */
    @JvmOverloads
    suspend fun createExecution(
        functionId: String,
        body: String? = null,
        async: Boolean? = null,
        path: String? = null,
        method: ExecutionMethod? = null,
        headers: Map<String, String>? = null,
        scheduledAt: String? = null,
    ): Execution {
        val apiPath =
            "/functions/{functionId}/executions"
                .replace("{functionId}", functionId)

        val apiParams =
            listOf(
                ClientParam.StringParam("body", body),
                ClientParam.StringParam("async", async?.toString()),
                ClientParam.StringParam("path", path),
                ClientParam.StringParam("method", method?.value),
                ClientParam.MapParam("headers", headers ?: mapOf()),
                ClientParam.StringParam("scheduledAt", scheduledAt),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Post,
            apiPath,
            Execution.serializer(),
            apiHeaders,
            apiParams,
        )
    }

    /**
     * Get execution
     *
     * Get a function execution log by its unique ID.
     *
     * @param functionId Function ID.
     * @param executionId Execution ID.
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.Execution]
     */
    suspend fun getExecution(
        functionId: String,
        executionId: String,
    ): Execution {
        val apiPath =
            "/functions/{functionId}/executions/{executionId}"
                .replace("{functionId}", functionId)
                .replace("{executionId}", executionId)

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            Execution.serializer(),
            apiHeaders,
        )
    }
}
