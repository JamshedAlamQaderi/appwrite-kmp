package com.jamshedalamqaderi.appwrite.kmp

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.BrowserUserAgent

internal actual fun httpEngine(): HttpClientEngine = Js.create()

internal actual fun HttpClientConfig<*>.platformUserAgent(
    appName: String,
    appPackageName: String,
    appVersion: String,
) {
    BrowserUserAgent()
}

internal actual fun appwriteFallbackCookie(): String {
    return ""
}