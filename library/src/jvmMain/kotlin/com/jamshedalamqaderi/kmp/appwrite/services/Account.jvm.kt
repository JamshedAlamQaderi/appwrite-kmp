package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException

actual suspend fun launchOAuth2Url(
    authUrl: String,
    callbackScheme: String,
): String = throw AppwriteException("OAuth2 functionality is not implemented on desktop yet.")
