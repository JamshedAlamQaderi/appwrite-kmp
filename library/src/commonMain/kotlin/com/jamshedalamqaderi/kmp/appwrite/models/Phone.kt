package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Phone
 */
@Serializable
data class Phone(
    /**
     * Phone code.
     */
    @SerialName("code")
    val code: String,
    /**
     * Country two-character ISO 3166-1 alpha code.
     */
    @SerialName("countryCode")
    val countryCode: String,
    /**
     * Country name.
     */
    @SerialName("countryName")
    val countryName: String,
)
