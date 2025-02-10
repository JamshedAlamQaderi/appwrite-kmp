package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.OAuthWebViewController
import platform.UIKit.UIApplication

//actual suspend fun launchOAuth2Url(
//    authUrl: String,
//    callbackScheme: String,
//): String =
//    suspendCoroutine { cont ->
//        println("LaunchOAuth2Url")
//        val oauthVC =
//            OAuthWebViewController(authUrl, callbackScheme, onResult = { callbackUrl, error ->
//                println("Callback url: $callbackUrl | Error: $error")
//                when {
//                    error != null -> cont.resumeWith(Result.failure(AppwriteException(error)))
//                    callbackUrl == null ->
//                        cont
//                            .resumeWith(Result.failure(AppwriteException("Invalid callback URL")))
//
//                    else -> cont.resumeWith(Result.success(callbackUrl))
//                }
//            })
//        val keyWindow = UIApplication.sharedApplication.keyWindow
//        val rootVC =
//            keyWindow?.rootViewController
//                ?: return@suspendCoroutine cont
//                    .resumeWith(Result.failure(AppwriteException("No root view controller found")))
//        rootVC.presentViewController(oauthVC, animated = true, completion = null)
//    }

actual suspend fun launchOAuth2Url(
    authUrl: String,
    callbackScheme: String,
): String {
    val oauthVC =
        OAuthWebViewController(authUrl, callbackScheme, onResult = { callbackUrl, error ->
            println("Callback url: $callbackUrl | Error: $error")
        })
    val keyWindow = UIApplication.sharedApplication.keyWindow
    val rootVC = keyWindow?.rootViewController ?: return ""
    rootVC.presentViewController(oauthVC, animated = true, completion = null)
    return ""
}