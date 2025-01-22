package com.jamshedalamqaderi.appwrite.kmp.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

suspend fun <T> Collection<T>.forEachAsync(callback: suspend (T) -> Unit) =
    withContext(Dispatchers.Default) {
        map { async { callback.invoke(it) } }.awaitAll()
    }
