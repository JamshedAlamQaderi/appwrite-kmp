package com.jamshedalamqaderi.kmp.appwrite

import com.jamshedalamqaderi.kmp.appwrite.services.Account
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.url.URL

internal actual fun httpEngine(selfSigned: Boolean): HttpClientEngine = Js.create()

internal actual fun defaultHeaders(): MutableMap<String, String> =
    mutableMapOf(
        "content-type" to "application/json",
        "x-sdk-name" to "Web",
        "x-sdk-platform" to "client",
        "x-sdk-language" to "kotlin",
        "x-sdk-version" to "19.0.0",
        "x-appwrite-response-format" to "1.8.0",
    )

@OptIn(DelicateCoroutinesApi::class)
internal actual fun Client.onClientInit() {
    val url = URL(window.location.href)
    val secret = url.searchParams.get("secret") ?: return
    val userId = url.searchParams.get("userId") ?: return
    url.searchParams.delete("secret")
    url.searchParams.delete("userId")
    GlobalScope.launch {
        Account(this@onClientInit).createSession(userId, secret)
        window.location.href = url.toString()
    }
}
