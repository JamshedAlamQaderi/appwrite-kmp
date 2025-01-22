package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Currency
 */
@Serializable
data class Currency(
    /**
     * Currency symbol.
     */
    @SerialName("symbol")
    val symbol: String,
    /**
     * Currency name.
     */
    @SerialName("name")
    val name: String,
    /**
     * Currency native symbol.
     */
    @SerialName("symbolNative")
    val symbolNative: String,
    /**
     * Number of decimal digits.
     */
    @SerialName("decimalDigits")
    val decimalDigits: Long,
    /**
     * Currency digit rounding.
     */
    @SerialName("rounding")
    val rounding: Double,
    /**
     * Currency code in [ISO 4217-1](http://en.wikipedia.org/wiki/ISO_4217) three-character format.
     */
    @SerialName("code")
    val code: String,
    /**
     * Currency plural name
     */
    @SerialName("namePlural")
    val namePlural: String,
)
