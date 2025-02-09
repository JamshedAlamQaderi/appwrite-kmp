package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.Serializable

@Serializable
data class CookieCache(
    val cookie: Cookie,
    val createdAt: Long,
)
