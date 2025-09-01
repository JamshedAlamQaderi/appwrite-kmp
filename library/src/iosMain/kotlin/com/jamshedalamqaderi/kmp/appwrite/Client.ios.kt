package com.jamshedalamqaderi.kmp.appwrite

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSURLAuthenticationChallenge
import platform.Foundation.NSURLAuthenticationMethodServerTrust
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionAuthChallengePerformDefaultHandling
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
import platform.Foundation.NSURLSessionTask
import platform.Foundation.create
import platform.Foundation.serverTrust
import platform.UIKit.UIDevice

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
@Suppress("MISSING_DEPENDENCY_CLASS_IN_EXPRESSION_TYPE")
internal actual fun httpEngine(selfSigned: Boolean): HttpClientEngine = Darwin.create {
    if (selfSigned) {
        handleChallenge { _: NSURLSession, _: NSURLSessionTask?, challenge: NSURLAuthenticationChallenge, completionHandler ->
            val method = challenge.protectionSpace.authenticationMethod
            if (method == NSURLAuthenticationMethodServerTrust) {
                val trust = challenge.protectionSpace.serverTrust
                if (trust != null) {
                    val credential = NSURLCredential.create(trust)
                    completionHandler(NSURLSessionAuthChallengeUseCredential, credential)
                } else {
                    completionHandler(NSURLSessionAuthChallengePerformDefaultHandling, null)
                }
            } else {
                completionHandler(NSURLSessionAuthChallengePerformDefaultHandling, null)
            }
        }
    } else {
        handleChallenge { _: NSURLSession, _: NSURLSessionTask?, _: NSURLAuthenticationChallenge, completionHandler ->
            completionHandler(NSURLSessionAuthChallengePerformDefaultHandling, null)
        }
    }

}

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
