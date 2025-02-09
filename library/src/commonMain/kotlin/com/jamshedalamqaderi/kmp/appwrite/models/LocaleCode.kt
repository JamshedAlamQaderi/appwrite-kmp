package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * LocaleCode
 */
@Serializable
data class LocaleCode(
    /**
     * Locale codes in [ISO 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     */
    @SerialName("code")
    val code: String,
    /**
     * Locale name
     */
    @SerialName("name")
    val name: String,
)
