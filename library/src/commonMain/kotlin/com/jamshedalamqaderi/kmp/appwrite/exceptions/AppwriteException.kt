package com.jamshedalamqaderi.kmp.appwrite.exceptions

import kotlinx.serialization.Serializable

@Serializable
class AppwriteException(
    override val message: String? = null,
    val code: Int? = null,
    val type: String? = null,
    val response: String? = null,
    val version: String? = null,
) : Exception(message)
