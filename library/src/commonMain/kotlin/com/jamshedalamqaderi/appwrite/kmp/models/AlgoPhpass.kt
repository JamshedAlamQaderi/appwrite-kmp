package com.jamshedalamqaderi.appwrite.kmp.models

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
