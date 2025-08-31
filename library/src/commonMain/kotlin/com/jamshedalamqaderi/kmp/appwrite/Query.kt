package com.jamshedalamqaderi.kmp.appwrite

import com.jamshedalamqaderi.kmp.appwrite.extensions.fromJson
import com.jamshedalamqaderi.kmp.appwrite.extensions.toJson
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement

@Serializable
data class Query<T>(
    val method: String,
    val attribute: String? = null,
    val values: List<T>? = null,
) {
    override fun toString() = this.toJson()

    companion object {
        fun equal(
            attribute: String,
            value: String,
        ) = equal(attribute, value, String.serializer())

        fun <T> equal(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("equal", attribute, listOf(value)).toJson(serializer(nestedType))

        fun <T> notEqual(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("notEqual", attribute, listOf(value)).toJson(serializer(nestedType))

        fun <T> lessThan(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("lessThan", attribute, listOf(value)).toJson(serializer(nestedType))

        fun <T> lessThanEqual(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("lessThanEqual", attribute, listOf(value)).toJson(serializer(nestedType))

        fun <T> greaterThan(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("greaterThan", attribute, listOf(value)).toJson(serializer(nestedType))

        fun <T> greaterThanEqual(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("greaterThanEqual", attribute, listOf(value)).toJson(serializer(nestedType))

        fun search(
            attribute: String,
            value: String,
        ) = Query("search", attribute, listOf(value)).toJson()

        fun isNull(attribute: String) = Query<String>("isNull", attribute).toJson()

        fun isNotNull(attribute: String) = Query<String>("isNotNull", attribute).toJson()

        fun <T> between(
            attribute: String,
            start: T,
            end: T,
            nestedType: KSerializer<T>,
        ) = Query("between", attribute, listOf(start, end)).toJson(serializer(nestedType))

        fun startsWith(
            attribute: String,
            value: String,
        ) = Query("startsWith", attribute, listOf(value)).toJson()

        fun endsWith(
            attribute: String,
            value: String,
        ) = Query("endsWith", attribute, listOf(value)).toJson()

        fun select(attributes: List<String>) = Query("select", null, attributes).toJson()

        fun orderAsc(attribute: String) = Query<String>("orderAsc", attribute).toJson()

        fun orderDesc(attribute: String) = Query<String>("orderDesc", attribute).toJson()

        fun cursorBefore(documentId: String) = Query("cursorBefore", null, listOf(documentId)).toJson()

        fun cursorAfter(documentId: String) = Query("cursorAfter", null, listOf(documentId)).toJson()

        fun limit(limit: Int) = Query("limit", null, listOf(limit)).toJson()

        fun offset(offset: Int) = Query("offset", null, listOf(offset)).toJson()

        fun contains(
            attribute: String,
            value: String,
        ) = contains(attribute, value, String.serializer())

        fun <T> contains(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("contains", attribute, listOf(value)).toJson(serializer(nestedType))

        fun notContains(
            attribute: String,
            value: String,
        ) = notContains(attribute, value, String.serializer())

        fun <T> notContains(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("notContains", attribute, listOf(value)).toJson(serializer(nestedType))

        fun notSearch(
            attribute: String,
            value: String,
        ) = Query("notSearch", attribute, listOf(value)).toJson()

        fun <T> notBetween(
            attribute: String,
            start: T,
            end: T,
            nestedType: KSerializer<T>,
        ) = Query("notBetween", attribute, listOf(start, end)).toJson(serializer(nestedType))

        fun notStartsWith(
            attribute: String,
            value: String,
        ) = Query("notStartsWith", attribute, listOf(value)).toJson()

        fun notEndsWith(
            attribute: String,
            value: String,
        ) = Query("notEndsWith", attribute, listOf(value)).toJson()

        fun createdBefore(value: String) = Query<String>("createdBefore", null, listOf(value)).toJson()

        fun createdAfter(value: String) = Query<String>("createdAfter", null, listOf(value)).toJson()

        fun updatedBefore(value: String) = Query<String>("updatedBefore", null, listOf(value)).toJson()

        fun updatedAfter(value: String) = Query<String>("updatedAfter", null, listOf(value)).toJson()

        fun or(queries: List<String>) =
            Query(
                "or",
                null,
                queries.map { it.fromJson(serializer(JsonElement.serializer())) },
            ).toJson()

        fun and(queries: List<String>) =
            Query(
                "and",
                null,
                queries.map { it.fromJson(serializer(JsonElement.serializer())) },
            ).toJson()
    }
}
