package com.jamshedalamqaderi.kmp.appwrite.services

import kotlinx.browser.window

actual suspend fun launchOAuth2Url(
    authUrl: String,
    callbackScheme: String,
): String {
    window.location.href = authUrl
    return ""
}
