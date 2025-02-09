package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * JWT
 */
@Serializable
data class Jwt(
    /**
     * JWT encoded string.
     */
    @SerialName("jwt")
    val jwt: String,
)
