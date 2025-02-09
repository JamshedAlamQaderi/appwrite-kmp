package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Continent
 */
@Serializable
data class Continent(
    /**
     * Continent name.
     */
    @SerialName("name")
    val name: String,
    /**
     * Continent two letter code.
     */
    @SerialName("code")
    val code: String,
)
