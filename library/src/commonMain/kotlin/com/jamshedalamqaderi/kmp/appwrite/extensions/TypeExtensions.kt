package com.jamshedalamqaderi.kmp.appwrite.extensions

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

/**
 * Provides a KSerializer for Map<String, String>.
 * Useful when encoding/decoding header-like maps consistently across APIs.
 */
fun mapSerializer(): KSerializer<Map<String, String>> = MapSerializer(String.serializer(), String.serializer())
