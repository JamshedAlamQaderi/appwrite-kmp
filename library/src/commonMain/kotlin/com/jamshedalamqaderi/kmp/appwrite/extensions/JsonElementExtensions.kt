package com.jamshedalamqaderi.kmp.appwrite.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun JsonElement.getString(key: String): String {
    return jsonObject[key]?.jsonPrimitive?.contentOrNull ?: ""
}

fun JsonElement.getStringList(key: String): List<String> {
    return jsonObject[key]?.jsonArray?.map {
        it.jsonPrimitive.content
    } ?: emptyList()
}

fun JsonElement.getLocalDateTimeOrNUll(key: String): LocalDateTime? {
    return try {
        getLocalDateTime(key)
    } catch (_: Exception) {
        null
    }
}

fun JsonElement.getLocalDateTime(key: String): LocalDateTime {
    val time = getString(key)
    return DateTimeComponents.Formats
        .ISO_DATE_TIME_OFFSET
        .parse(time)
        .toLocalDateTime()
}
