package com.jamshedalamqaderi.kmp.appwrite.extensions

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal val json = Json { ignoreUnknownKeys = true }

internal inline fun <reified T> String.fromJson(): T = json.decodeFromString<T>(this)

internal fun <T> String.fromJson(deserializer: DeserializationStrategy<T>): T = json.decodeFromString(deserializer, this)

internal fun <T> String.tryFromJson(deserializer: DeserializationStrategy<T>): T? =
    try {
        fromJson(deserializer)
    } catch (_: Exception) {
        null
    }

internal inline fun <reified T> T.toJson(): String = json.encodeToString(this)

internal fun <T> T.toJson(serializer: SerializationStrategy<T>): String = json.encodeToString(serializer, this)

internal fun <T, R> T.jsonCast(
    serializer: SerializationStrategy<T>,
    deserializer: DeserializationStrategy<R>,
): R = toJson(serializer).fromJson(deserializer)

internal inline fun <reified T> T.tryJsonCast(): T? =
    try {
        toJson().fromJson<T>()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
