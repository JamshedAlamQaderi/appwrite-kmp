package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.OAuthWebViewController
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import platform.UIKit.UIApplication
import kotlin.coroutines.suspendCoroutine

actual suspend fun launchOAuth2Url(
    authUrl: String,
    callbackScheme: String,
): String =
    suspendCoroutine { cont ->
        val oauthVC =
            OAuthWebViewController(authUrl, callbackScheme, onResult = { callbackUrl, error ->
                when {
                    error != null -> cont.resumeWith(Result.failure(AppwriteException(error)))
                    callbackUrl == null ->
                        cont
                            .resumeWith(Result.failure(AppwriteException("Invalid callback URL")))

                    else -> cont.resumeWith(Result.success(callbackUrl))
                }
            })
        val keyWindow = UIApplication.sharedApplication.keyWindow
        val rootVC =
            keyWindow?.rootViewController
                ?: return@suspendCoroutine cont
                    .resumeWith(Result.failure(AppwriteException("No root view controller found")))
        rootVC.presentViewController(oauthVC, animated = true, completion = null)
    }
