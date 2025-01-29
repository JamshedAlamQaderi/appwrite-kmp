package com.jamshedalamqaderi.appwrite.kmp

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIViewController

internal actual fun httpEngine(): HttpClientEngine = Darwin.create()

internal actual fun defaultHeaders(): MutableMap<String, String> {
    return mutableMapOf(
        "content-type" to "application/json",
        "x-sdk-name" to "Apple",
        "x-sdk-platform" to "client",
        "x-sdk-language" to "apple",
        "x-sdk-version" to "8.0.0",
        "x-appwrite-response-format" to "1.6.0",
    )
}

object AppwriteViewControllerExtension {
    fun registerOAuthHandler(view: UIViewController) {
    }
}
