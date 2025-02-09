package com.jamshedalamqaderi.kmp.appwrite

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

internal actual fun httpEngine(): HttpClientEngine = Js.create()

internal actual fun defaultHeaders(): MutableMap<String, String> {
    return mutableMapOf(
        "content-type" to "application/json",
        "x-sdk-name" to "Web",
        "x-sdk-platform" to "client",
        "x-sdk-language" to "kotlin",
        "x-sdk-version" to "5.1.1",
        "x-appwrite-response-format" to "1.5.7",
    )
}
