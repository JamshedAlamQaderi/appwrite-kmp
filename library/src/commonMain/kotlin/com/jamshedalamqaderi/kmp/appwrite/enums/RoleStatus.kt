package com.jamshedalamqaderi.kmp.appwrite.enums

enum class RoleStatus(private val value: String) {
    None(""),
    Verified("verified"),
    Unverified("unverified"),
    ;

    override fun toString() = value
}
