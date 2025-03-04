package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.models.ClientParam
import com.jamshedalamqaderi.kmp.appwrite.models.Subscriber
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.JsonElement

/**
 * The Messaging service allows you to send messages to any provider type (SMTP, push notification, SMS, etc.).
 **/
class Messaging(client: Client) : Service(client) {
    /**
     * Create subscriber
     *
     * Create a new subscriber.
     *
     * @param topicId Topic ID. The topic ID to subscribe to.
     * @param subscriberId Subscriber ID. Choose a custom Subscriber ID or a new Subscriber ID.
     * @param targetId Target ID. The target ID to link to the specified Topic ID.
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.Subscriber]
     */
    suspend fun createSubscriber(
        topicId: String,
        subscriberId: String,
        targetId: String,
    ): Subscriber {
        val apiPath =
            "/messaging/topics/{topicId}/subscribers"
                .replace("{topicId}", topicId)

        val apiParams =
            listOf(
                ClientParam.StringParam("subscriberId", subscriberId),
                ClientParam.StringParam("targetId", targetId),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            Subscriber.serializer(),
            apiHeaders,
            apiParams,
        )
    }

    /**
     * Delete subscriber
     *
     * Delete a subscriber by its unique ID.
     *
     * @param topicId Topic ID. The topic ID subscribed to.
     * @param subscriberId Subscriber ID.
     * @return [JsonElement]
     */
    suspend fun deleteSubscriber(
        topicId: String,
        subscriberId: String,
    ): JsonElement {
        val apiPath =
            "/messaging/topics/{topicId}/subscribers/{subscriberId}"
                .replace("{topicId}", topicId)
                .replace("{subscriberId}", subscriberId)

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Delete,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
        )
    }
}
