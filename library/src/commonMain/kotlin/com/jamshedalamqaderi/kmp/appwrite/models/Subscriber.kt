package com.jamshedalamqaderi.kmp.appwrite.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Subscriber
 */
@OptIn(ExperimentalTime::class)
@Serializable
data class Subscriber(
    /**
     * Subscriber ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * Subscriber creation time in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: Instant,
    /**
     * Subscriber update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: Instant,
    /**
     * Target ID.
     */
    @SerialName("targetId")
    val targetId: String,
    /**
     * Target.
     */
    @SerialName("target")
    val target: Target,
    /**
     * Topic ID.
     */
    @SerialName("userId")
    val userId: String,
    /**
     * User Name.
     */
    @SerialName("userName")
    val userName: String,
    /**
     * Topic ID.
     */
    @SerialName("topicId")
    val topicId: String,
    /**
     * The target provider type. Can be one of the following: `email`, `sms` or `push`.
     */
    @SerialName("providerType")
    val providerType: String,
)
