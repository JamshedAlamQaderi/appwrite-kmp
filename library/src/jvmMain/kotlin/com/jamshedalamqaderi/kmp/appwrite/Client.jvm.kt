package com.jamshedalamqaderi.kmp.appwrite

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

internal actual fun httpEngine(selfSigned: Boolean): HttpClientEngine =
    OkHttp.create {
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

internal actual fun defaultHeaders(): MutableMap<String, String> {
    val osName = System.getProperty("os.name")
    val osVersion = System.getProperty("os.version")
    val osArch = System.getProperty("os.arch")
    val javaVersion = System.getProperty("java.version")
    return mutableMapOf(
        "content-type" to "application/json",
        "user-agent" to "AppwriteKMP/11.0.0 ($osName; $osVersion; $osArch) Java/$javaVersion",
        "x-sdk-name" to "Kotlin",
        "x-sdk-platform" to "client",
        "x-sdk-language" to "kotlin",
        "x-sdk-version" to "11.0.0",
        "x-appwrite-response-format" to "1.8.0",
    )
}

internal actual fun Client.onClientInit() {
}
