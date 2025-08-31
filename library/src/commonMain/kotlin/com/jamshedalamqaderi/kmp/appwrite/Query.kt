package com.jamshedalamqaderi.kmp.appwrite

import com.jamshedalamqaderi.kmp.appwrite.extensions.fromJson
import com.jamshedalamqaderi.kmp.appwrite.extensions.toJson
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement

@Serializable
/**
 * Represents an Appwrite query expression.
 *
 * This is a small serializable DTO used internally to build JSON query
 * strings that Appwrite Database and other services accept. You typically
 * won't instantiate this class directly. Instead, call one of the
 * factory helpers in the companion object (e.g. Query.equal, Query.greaterThan,
 * Query.orderAsc, Query.and) which return the properly formatted JSON string
 * that the Appwrite SDK expects.
 *
 * Example usage:
 * - Query.equal("status", "active")
 * - Query.greaterThan("age", 18, Int.serializer())
 * - Query.and(listOf(
 *     Query.equal("country", "US"),
 *     Query.lessThanEqual("score", 100, Int.serializer())
 *   ))
 *
 * Note: Overloads that accept a generic value require passing a Kotlinx
 * Serialization KSerializer for that type so the value can be encoded
 * correctly for the Appwrite API.
 */
data class Query<T>(
    val method: String,
    val attribute: String? = null,
    val values: List<T>? = null,
) {
    override fun toString() = this.toJson()

    companion object {
        /** Creates an equality filter for [attribute] equal to [value]. */
        fun equal(
            attribute: String,
            value: String,
        ) = equal(attribute, value, String.serializer())

        /** Creates an equality filter for [attribute] equal to [value]. Requires [nestedType] to serialize the value. */
        fun <T> equal(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("equal", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Creates a filter for [attribute] not equal to [value]. Requires [nestedType] to serialize the value. */
        fun <T> notEqual(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("notEqual", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Creates a filter for [attribute] strictly less than [value]. Requires [nestedType] to serialize the value. */
        fun <T> lessThan(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("lessThan", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Creates a filter for [attribute] less than or equal to [value]. Requires [nestedType] to serialize the value. */
        fun <T> lessThanEqual(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("lessThanEqual", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Creates a filter for [attribute] strictly greater than [value]. Requires [nestedType] to serialize the value. */
        fun <T> greaterThan(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("greaterThan", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Creates a filter for [attribute] greater than or equal to [value]. Requires [nestedType] to serialize the value. */
        fun <T> greaterThanEqual(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("greaterThanEqual", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Full-text search for [value] within the [attribute]. */
        fun search(
            attribute: String,
            value: String,
        ) = Query("search", attribute, listOf(value)).toJson()

        /** Matches documents where [attribute] is null. */
        fun isNull(attribute: String) = Query<String>("isNull", attribute).toJson()

        /** Matches documents where [attribute] is not null. */
        fun isNotNull(attribute: String) = Query<String>("isNotNull", attribute).toJson()

        /** Matches documents where [attribute] is between [start] and [end] (inclusive). Requires [nestedType] to serialize values. */
        fun <T> between(
            attribute: String,
            start: T,
            end: T,
            nestedType: KSerializer<T>,
        ) = Query("between", attribute, listOf(start, end)).toJson(serializer(nestedType))

        /** Matches documents where [attribute] starts with [value]. */
        fun startsWith(
            attribute: String,
            value: String,
        ) = Query("startsWith", attribute, listOf(value)).toJson()

        /** Matches documents where [attribute] ends with [value]. */
        fun endsWith(
            attribute: String,
            value: String,
        ) = Query("endsWith", attribute, listOf(value)).toJson()

        /** Select a subset of [attributes] to be returned in result documents. */
        fun select(attributes: List<String>) = Query("select", null, attributes).toJson()

        /** Orders results ascending by [attribute]. */
        fun orderAsc(attribute: String) = Query<String>("orderAsc", attribute).toJson()

        /** Orders results descending by [attribute]. */
        fun orderDesc(attribute: String) = Query<String>("orderDesc", attribute).toJson()

        /** Paginates results to start before the document with [documentId]. */
        fun cursorBefore(documentId: String) = Query("cursorBefore", null, listOf(documentId)).toJson()

        /** Paginates results to start after the document with [documentId]. */
        fun cursorAfter(documentId: String) = Query("cursorAfter", null, listOf(documentId)).toJson()

        /** Limits the number of returned documents to [limit]. */
        fun limit(limit: Int) = Query("limit", null, listOf(limit)).toJson()

        /** Skips [offset] documents before returning results. */
        fun offset(offset: Int) = Query("offset", null, listOf(offset)).toJson()

        /** Matches documents where [attribute] contains [value]. */
        fun contains(
            attribute: String,
            value: String,
        ) = contains(attribute, value, String.serializer())

        /** Matches documents where [attribute] contains [value]. Requires [nestedType] to serialize the value. */
        fun <T> contains(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("contains", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Matches documents where [attribute] does not contain [value]. */
        fun notContains(
            attribute: String,
            value: String,
        ) = notContains(attribute, value, String.serializer())

        /** Matches documents where [attribute] does not contain [value]. Requires [nestedType] to serialize the value. */
        fun <T> notContains(
            attribute: String,
            value: T,
            nestedType: KSerializer<T>,
        ) = Query("notContains", attribute, listOf(value)).toJson(serializer(nestedType))

        /** Matches documents where a full-text search on [attribute] for [value] does not match. */
        fun notSearch(
            attribute: String,
            value: String,
        ) = Query("notSearch", attribute, listOf(value)).toJson()

        /** Matches documents where [attribute] is NOT between [start] and [end]. Requires [nestedType] to serialize values. */
        fun <T> notBetween(
            attribute: String,
            start: T,
            end: T,
            nestedType: KSerializer<T>,
        ) = Query("notBetween", attribute, listOf(start, end)).toJson(serializer(nestedType))

        /** Matches documents where [attribute] does not start with [value]. */
        fun notStartsWith(
            attribute: String,
            value: String,
        ) = Query("notStartsWith", attribute, listOf(value)).toJson()

        /** Matches documents where [attribute] does not end with [value]. */
        fun notEndsWith(
            attribute: String,
            value: String,
        ) = Query("notEndsWith", attribute, listOf(value)).toJson()

        /** Matches documents created before the given ISO 8601 datetime [value]. */
        fun createdBefore(value: String) = Query<String>("createdBefore", null, listOf(value)).toJson()

        /** Matches documents created after the given ISO 8601 datetime [value]. */
        fun createdAfter(value: String) = Query<String>("createdAfter", null, listOf(value)).toJson()

        /** Matches documents updated before the given ISO 8601 datetime [value]. */
        fun updatedBefore(value: String) = Query<String>("updatedBefore", null, listOf(value)).toJson()

        /** Matches documents updated after the given ISO 8601 datetime [value]. */
        fun updatedAfter(value: String) = Query<String>("updatedAfter", null, listOf(value)).toJson()

        /** Combines the provided [queries] with a logical OR. Each element must be a JSON string produced by Query helpers. */
        fun or(queries: List<String>) =
            Query(
                "or",
                null,
                queries.map { it.fromJson(serializer(JsonElement.serializer())) },
            ).toJson()

        /** Combines the provided [queries] with a logical AND. Each element must be a JSON string produced by Query helpers. */
        fun and(queries: List<String>) =
            Query(
                "and",
                null,
                queries.map { it.fromJson(serializer(JsonElement.serializer())) },
            ).toJson()
    }
}
