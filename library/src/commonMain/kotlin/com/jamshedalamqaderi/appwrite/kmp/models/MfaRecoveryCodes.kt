package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * MFA Recovery Codes
 */
@Serializable
data class MfaRecoveryCodes(
    /**
     * Recovery codes.
     */
    @SerialName("recoveryCodes")
    val recoveryCodes: List<String>,
)
