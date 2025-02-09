package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * AlgoPHPass
 */
@Serializable
data class AlgoPhpass(
    /**
     * Algo type.
     */
    @SerialName("type")
    val type: String,
)
