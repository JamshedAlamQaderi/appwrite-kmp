package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * AlgoSHA
 */
@Serializable
data class AlgoSha(
    /**
     * Algo type.
     */
    @SerialName("type")
    val type: String,
)
