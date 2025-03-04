package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Preferences
 */
@Serializable
data class Preferences<T>(
    /**
     * Additional properties
     */
    @SerialName("data")
    val data: T? = null,
)
