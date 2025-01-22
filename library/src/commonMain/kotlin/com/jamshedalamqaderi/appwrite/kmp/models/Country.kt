package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Country
 */
@Serializable
data class Country(
    /**
     * Country name.
     */
    @SerialName("name")
    val name: String,
    /**
     * Country two-character ISO 3166-1 alpha code.
     */
    @SerialName("code")
    val code: String,
)
