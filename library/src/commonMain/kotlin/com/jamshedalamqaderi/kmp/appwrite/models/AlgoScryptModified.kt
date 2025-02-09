package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * AlgoScryptModified
 */
@Serializable
data class AlgoScryptModified(
    /**
     * Algo type.
     */
    @SerialName("type")
    val type: String,
    /**
     * Salt used to compute hash.
     */
    @SerialName("salt")
    val salt: String,
    /**
     * Separator used to compute hash.
     */
    @SerialName("saltSeparator")
    val saltSeparator: String,
    /**
     * Key used to compute hash.
     */
    @SerialName("signerKey")
    val signerKey: String,
)
