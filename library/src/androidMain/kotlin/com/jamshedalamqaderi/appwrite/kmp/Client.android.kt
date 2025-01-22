package com.jamshedalamqaderi.appwrite.kmp

import android.content.Context
import android.content.pm.PackageManager
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal actual fun httpEngine(): HttpClientEngine =
    OkHttp.create {
        preconfigured =
            OkHttpClient.Builder()
                .pingInterval(0, TimeUnit.MILLISECONDS)
                .build()
    }

private fun appVersion(context: Context): String? {
    return try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

internal actual fun defaultHeaders(): MutableMap<String, String> {
    val context = AppwriteKmpInitializer.context
    return mutableMapOf(
        "content-type" to "application/json",
        "origin" to "appwrite-android://${context.packageName}",
        "user-agent" to "${context.packageName}/${appVersion(context)}, ${System.getProperty("http.agent")}",
        "x-sdk-name" to "Android",
        "x-sdk-platform" to "client",
        "x-sdk-language" to "android",
        "x-sdk-version" to "5.1.1",
        "x-appwrite-response-format" to "1.5.7",
    )
}
