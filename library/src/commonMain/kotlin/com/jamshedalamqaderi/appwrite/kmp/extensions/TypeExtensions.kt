package com.jamshedalamqaderi.appwrite.kmp.extensions

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

inline fun <reified T : Any> classOf(): KClass<T> {
    @Suppress("UNCHECKED_CAST")
    return (typeOf<T>().classifier!! as KClass<T>)
}

fun mapSerializer(): KSerializer<Map<String, String>> {
    return MapSerializer(String.serializer(), String.serializer())
}
