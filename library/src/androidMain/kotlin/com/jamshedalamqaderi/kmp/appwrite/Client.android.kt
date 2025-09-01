package com.jamshedalamqaderi.kmp.appwrite

import android.content.Context
import android.content.pm.PackageManager
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

internal actual fun httpEngine(selfSigned: Boolean): HttpClientEngine =
    OkHttp.create {
        preconfigured =
            OkHttpClient
                .Builder()
                .pingInterval(0, TimeUnit.MILLISECONDS)
                .build()
        if (selfSigned) {
            config {
                val trustAllCerts =
                    arrayOf<javax.net.ssl.TrustManager>(
                        @Suppress("CustomX509TrustManager")
                        object : javax.net.ssl.X509TrustManager {
                            @Suppress("TrustAllX509TrustManager")
                            override fun checkClientTrusted(
                                chain: Array<out X509Certificate?>?,
                                authType: String?,
                            ) {
                            }

                            @Suppress("TrustAllX509TrustManager")
                            override fun checkServerTrusted(
                                chain: Array<out X509Certificate?>?,
                                authType: String?,
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<out X509Certificate?> = arrayOf()
                        },
                    )
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            }
        }
    }

private fun appVersion(context: Context): String? =
    try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
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
        "x-sdk-version" to "9.0.0",
        "x-appwrite-response-format" to "1.8.0",
    )
}

internal actual fun Client.onClientInit() {
}
