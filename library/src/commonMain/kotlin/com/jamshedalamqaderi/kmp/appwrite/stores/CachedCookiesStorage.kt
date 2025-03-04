package com.jamshedalamqaderi.kmp.appwrite.stores

import com.jamshedalamqaderi.kmp.appwrite.extensions.fillDefaults
import com.jamshedalamqaderi.kmp.appwrite.extensions.fromJson
import com.jamshedalamqaderi.kmp.appwrite.extensions.matches
import com.jamshedalamqaderi.kmp.appwrite.extensions.toJson
import com.jamshedalamqaderi.kmp.appwrite.models.CookieCache
import com.russhwolf.settings.Settings
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import io.ktor.util.date.getTimeMillis
import kotlinx.atomicfu.AtomicLong
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.min

private val settings = Settings()

class CachedCookiesStorage : CookiesStorage {
    private val oldestCookie: AtomicLong = atomic(0L)
    private val mutex = Mutex()

    init {
        val container = readAllCookies()
        val now = getTimeMillis()
        if (now >= oldestCookie.value) cleanup(now, container)
    }

    override suspend fun addCookie(
        requestUrl: Url,
        cookie: Cookie,
    ) {
        with(cookie) {
            if (name.isBlank()) return
        }
        mutex.withLock {
            val container = readAllCookies().toMutableList()
            container.removeAll { (existingCookie, _) ->
                existingCookie.name == cookie.name &&
                    existingCookie.toHttpCookie().matches(requestUrl)
            }
            val createdAt = getTimeMillis()
            container.add(
                CookieCache(
                    com.jamshedalamqaderi.kmp.appwrite.models.Cookie.from(
                        cookie.fillDefaults(requestUrl),
                    ),
                    createdAt,
                ),
            )

            cookie.maxAgeOrExpires(createdAt)?.let {
                if (oldestCookie.value > it) {
                    oldestCookie.value = it
                }
            }
            writeAllCookies(container)
        }
    }

    override fun close() {}

    override suspend fun get(requestUrl: Url): List<Cookie> =
        mutex.withLock {
            println("reading all cookies: $requestUrl")
            val container = readAllCookies()
            val now = getTimeMillis()
            if (now >= oldestCookie.value) cleanup(now, container)
            val cookies =
                container
                    .map { it.cookie.toHttpCookie() }
                    .filter { it.matches(requestUrl) }
            return@withLock cookies
        }

    private fun readAllCookies(): List<CookieCache> {
        return settings.getStringOrNull("cookies")
            ?.fromJson<List<CookieCache>>()
            ?: emptyList()
    }

    private fun writeAllCookies(cookies: List<CookieCache>) {
        settings.putString("cookies", cookies.toJson())
    }

    private fun cleanup(
        timestamp: Long,
        cachedCookies: List<CookieCache>,
    ) {
        val container = cachedCookies.toMutableList()
        container.removeAll { (cookie, createdAt) ->
            val expires = cookie.toHttpCookie().maxAgeOrExpires(createdAt) ?: return@removeAll false
            expires < timestamp
        }

        val newOldest =
            container.fold(Long.MAX_VALUE) { acc, (cookie, createdAt) ->
                cookie.toHttpCookie().maxAgeOrExpires(createdAt)?.let { min(acc, it) } ?: acc
            }

        oldestCookie.value = newOldest
    }

    private fun Cookie.maxAgeOrExpires(createdAt: Long): Long? = maxAge?.let { createdAt + (it * 1000) }
}
