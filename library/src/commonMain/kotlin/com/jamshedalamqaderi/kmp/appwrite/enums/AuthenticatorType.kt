package com.jamshedalamqaderi.kmp.appwrite.enums

import kotlinx.serialization.SerialName

enum class AuthenticatorType(val value: String) {
    @SerialName("totp")
    TOTP("totp"),
    ;

    override fun toString() = value
}
