package com.jamshedalamqaderi.appwrite.kmp

import com.jamshedalamqaderi.appwrite.kmp.extensions.toJson
import kotlinx.serialization.Serializable

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
        ) = Query("equal", attribute, listOf(value)).toJson()

        fun <T> notEqual(
            attribute: String,
            value: T,
        ) = Query("notEqual", attribute, listOf(value)).toJson()

        fun <T> lessThan(
            attribute: String,
            value: T,
        ) = Query("lessThan", attribute, listOf(value)).toJson()

        fun <T> lessThanEqual(
            attribute: String,
            value: T,
        ) = Query("lessThanEqual", attribute, listOf(value)).toJson()

        fun <T> greaterThan(
            attribute: String,
            value: T,
        ) = Query("greaterThan", attribute, listOf(value)).toJson()

        fun <T> greaterThanEqual(
            attribute: String,
            value: T,
        ) = Query("greaterThanEqual", attribute, listOf(value)).toJson()

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
        ) = Query("between", attribute, listOf(start, end)).toJson()

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
        ) = Query("contains", attribute, listOf(value)).toJson()

        fun or(queries: List<String>) = Query("or", null, queries).toJson()

        fun and(queries: List<String>) = Query("and", null, queries).toJson()
    }
}
