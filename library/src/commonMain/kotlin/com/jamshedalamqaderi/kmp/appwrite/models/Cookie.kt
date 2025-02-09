package com.jamshedalamqaderi.kmp.appwrite.models

import io.ktor.http.CookieEncoding
import io.ktor.util.date.GMTDate
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName

@Serializable
data class Cookie(
    val name: String,
    val value: String,
    val encoding: CookieEncoding = CookieEncoding.URI_ENCODING,
    @get:JvmName("getMaxAgeInt")
    val maxAge: Int? = null,
    val expires: Long? = null,
    val domain: String? = null,
    val path: String? = null,
    val secure: Boolean = false,
    val httpOnly: Boolean = false,
    val extensions: Map<String, String?> = emptyMap(),
) {
    fun toHttpCookie(): io.ktor.http.Cookie {
        return io.ktor.http.Cookie(
            name,
            value,
            encoding,
            maxAge ?: 0,
            expires?.let { GMTDate(it) },
            domain,
            path,
            secure,
            httpOnly,
            extensions,
        )
    }

    companion object {
        fun from(cookie: io.ktor.http.Cookie): Cookie {
            return Cookie(
                cookie.name,
                cookie.value,
                cookie.encoding,
                cookie.maxAge,
                cookie.expires?.timestamp,
                cookie.domain,
                cookie.path,
                cookie.secure,
                cookie.httpOnly,
                cookie.extensions,
            )
        }
    }
}
