package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * MFAFactors
 */
@Serializable
data class MfaFactors(
    /**
     * Can TOTP be used for MFA challenge for this account.
     */
    @SerialName("totp")
    val totp: Boolean,
    /**
     * Can phone (SMS) be used for MFA challenge for this account.
     */
    @SerialName("phone")
    val phone: Boolean,
    /**
     * Can email be used for MFA challenge for this account.
     */
    @SerialName("email")
    val email: Boolean,
    /**
     * Can recovery code be used for MFA challenge for this account.
     */
    @SerialName("recoveryCode")
    val recoveryCode: Boolean,
)
