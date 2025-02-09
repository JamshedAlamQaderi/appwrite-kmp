package com.jamshedalamqaderi.kmp.appwrite.enums

import kotlinx.serialization.SerialName

enum class AuthenticationFactor(val value: String) {
    @SerialName("email")
    EMAIL("email"),

    @SerialName("phone")
    PHONE("phone"),

    @SerialName("totp")
    TOTP("totp"),

    @SerialName("recoverycode")
    RECOVERYCODE("recoverycode"),
    ;

    override fun toString() = value
}
