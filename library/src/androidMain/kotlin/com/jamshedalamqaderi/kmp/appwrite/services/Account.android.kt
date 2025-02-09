package com.jamshedalamqaderi.kmp.appwrite.services

import android.net.Uri
import androidx.activity.ComponentActivity
import com.jamshedalamqaderi.kmp.appwrite.AppwriteActivityLifecycleCallbacks
import com.jamshedalamqaderi.kmp.appwrite.WebAuthComponent
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine

actual suspend fun launchOAuth2Url(
    authUrl: String,
    callbackScheme: String,
): String =
    suspendCoroutine { cont ->
        val activity =
            AppwriteActivityLifecycleCallbacks.getActivity()
                ?: throw AppwriteException("Missing activity. Register AppwriteActivityLifecycleCallbacks in MainActivity.")
        if (activity !is ComponentActivity) throw AppwriteException("Expected ComponentActivity but found ${activity::class.simpleName}.")
        runBlocking {
            WebAuthComponent.authenticate(activity, Uri.parse(authUrl), callbackScheme) { result ->
                val error = result.exceptionOrNull()?.message
                val callbackUrl = result.getOrNull()
                when {
                    error != null -> cont.resumeWith(Result.failure(AppwriteException(error)))
                    callbackUrl == null ->
                        cont
                            .resumeWith(Result.failure(AppwriteException("Invalid callback URL")))

                    else -> cont.resumeWith(Result.success(callbackUrl))
                }
            }
        }
    }
