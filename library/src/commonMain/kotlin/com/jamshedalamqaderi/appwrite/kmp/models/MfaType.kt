package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * MFAType
 */
@Serializable
data class MfaType(
    /**
     * Secret token used for TOTP factor.
     */
    @SerialName("secret")
    val secret: String,
    /**
     * URI for authenticator apps.
     */
    @SerialName("uri")
    val uri: String,
)
