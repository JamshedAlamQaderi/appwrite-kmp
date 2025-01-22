package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * AlgoScrypt
 */
@Serializable
data class AlgoScrypt(
    /**
     * Algo type.
     */
    @SerialName("type")
    val type: String,
    /**
     * CPU complexity of computed hash.
     */
    @SerialName("costCpu")
    val costCpu: Long,
    /**
     * Memory complexity of computed hash.
     */
    @SerialName("costMemory")
    val costMemory: Long,
    /**
     * Parallelization of computed hash.
     */
    @SerialName("costParallel")
    val costParallel: Long,
    /**
     * Length used to compute hash.
     */
    @SerialName("length")
    val length: Long,
)
