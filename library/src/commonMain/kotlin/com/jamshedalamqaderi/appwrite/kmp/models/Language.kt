package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Language
 */
@Serializable
data class Language(
    /**
     * Language name.
     */
    @SerialName("name")
    val name: String,
    /**
     * Language two-character ISO 639-1 codes.
     */
    @SerialName("code")
    val code: String,
    /**
     * Language native name.
     */
    @SerialName("nativeName")
    val nativeName: String,
)
