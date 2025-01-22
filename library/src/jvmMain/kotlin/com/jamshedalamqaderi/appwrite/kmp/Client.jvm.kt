package com.jamshedalamqaderi.appwrite.kmp

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun httpEngine(): HttpClientEngine = OkHttp.create()

internal actual fun defaultHeaders(): MutableMap<String, String> {
    val osName = System.getProperty("os.name")
    val osVersion = System.getProperty("os.version")
    val osArch = System.getProperty("os.arch")
    val javaVersion = System.getProperty("java.version")
    return mutableMapOf(
        "content-type" to "application/json",
        "user-agent" to "Appwrite-kmp/1.0.0 ($osName; $osVersion; $osArch) Java/$javaVersion",
        "x-sdk-name" to "Kotlin",
        "x-sdk-platform" to "client",
        "x-sdk-language" to "kotlin",
        "x-sdk-version" to "5.1.1",
        "x-appwrite-response-format" to "1.5.7",
    )
}
