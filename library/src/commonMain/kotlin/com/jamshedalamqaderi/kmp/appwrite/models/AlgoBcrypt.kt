package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * AlgoBcrypt
 */
@Serializable
data class AlgoBcrypt(
    /**
     * Algo type.
     */
    @SerialName("type")
    val type: String,
)
