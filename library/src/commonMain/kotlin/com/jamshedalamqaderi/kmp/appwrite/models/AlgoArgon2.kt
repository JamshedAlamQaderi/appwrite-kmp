package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * AlgoArgon2
 */
@Serializable
data class AlgoArgon2(
    /**
     * Algo type.
     */
    @SerialName("type")
    val type: String,
    /**
     * Memory used to compute hash.
     */
    @SerialName("memoryCost")
    val memoryCost: Long,
    /**
     * Amount of time consumed to compute hash
     */
    @SerialName("timeCost")
    val timeCost: Long,
    /**
     * Number of threads used to compute hash.
     */
    @SerialName("threads")
    val threads: Long,
)
