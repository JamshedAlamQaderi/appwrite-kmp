package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * AlgoMD5
 */
@Serializable
data class AlgoMd5(
    /**
     * Algo type.
     */
    @SerialName("type")
    val type: String,
)
