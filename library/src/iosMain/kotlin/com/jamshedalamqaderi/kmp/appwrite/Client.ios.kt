package com.jamshedalamqaderi.kmp.appwrite

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSBundle
import platform.Foundation.NSProcessInfo
import platform.UIKit.UIDevice

internal actual fun httpEngine(selfSigned: Boolean): HttpClientEngine = Darwin.create()

internal actual fun defaultHeaders(): MutableMap<String, String> {
    return mutableMapOf(
        "content-type" to "application/json",
        "x-sdk-name" to "Apple",
        "x-sdk-platform" to "client",
        "x-sdk-language" to "apple",
        "x-sdk-version" to "11.0.0",
        "x-appwrite-response-format" to "1.8.0",
        "user-agent" to getUserAgent(),
        "origin" to "appwrite-${UIDevice.currentDevice.systemName.lowercase()}://${NSBundle.mainBundle.bundleIdentifier ?: ""}",
    )
}

private fun getUserAgent(): String {
    val systemName = UIDevice.currentDevice.systemName
    val systemVersion = UIDevice.currentDevice.systemVersion
    val model = UIDevice.currentDevice.model
    val osVersion = NSProcessInfo.processInfo.operatingSystemVersionString
    val bundle = NSBundle.mainBundle
    val version = bundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: ""
    val device = "$model; $systemName/$systemVersion ($osVersion)"
    return "${bundle.bundleIdentifier ?: ""}/$version $device"
}

internal actual fun Client.onClientInit() {
}
