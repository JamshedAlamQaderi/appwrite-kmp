package com.jamshedalamqaderi.kmp.appwrite.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement

suspend fun <T> Collection<T>.forEachAsync(callback: suspend (T) -> Unit) =
    withContext(Dispatchers.Default) {
        map { async { callback.invoke(it) } }.awaitAll()
    }

@Suppress("UNCHECKED_CAST")
internal fun Any.encodeUnknownValue(): JsonElement {
    val elementSerializer: KSerializer<Any?> = when (this) {
        is String -> String.serializer()
        is Int -> Int.serializer()
        is Long -> Long.serializer()
        is Boolean -> Boolean.serializer()
        is Double -> Double.serializer()
        is Float -> Float.serializer()
        is JsonElement -> JsonElement.serializer()
        is KSerializer<*> -> error("You passed a list of KSerializer objects, not data!")
        else -> throw SerializationException(
            "Don’t know how to serialise elements of type ${this::class.qualifiedName}"
        )
    } as KSerializer<Any?>
    return json.encodeToJsonElement(elementSerializer, this)
}