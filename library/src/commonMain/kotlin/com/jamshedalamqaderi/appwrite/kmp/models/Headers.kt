package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Headers
 */
@Serializable
data class Headers(
    /**
     * Header name.
     */
    @SerialName("name")
    val name: String,
    /**
     * Header value.
     */
    @SerialName("value")
    val value: String,
)
