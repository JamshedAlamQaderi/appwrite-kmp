package com.jamshedalamqaderi.appwrite.kmp.enums

import kotlinx.serialization.SerialName

enum class ExecutionMethod(private val value: String) {
    @SerialName("GET")
    GET("GET"),

    @SerialName("POST")
    POST("POST"),

    @SerialName("PUT")
    PUT("PUT"),

    @SerialName("PATCH")
    PATCH("PATCH"),

    @SerialName("DELETE")
    DELETE("DELETE"),

    @SerialName("OPTIONS")
    OPTIONS("OPTIONS"),
    ;

    override fun toString() = value
}
