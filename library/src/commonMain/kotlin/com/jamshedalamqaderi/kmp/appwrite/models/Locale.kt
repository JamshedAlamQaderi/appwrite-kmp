package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Locale
 */
@Serializable
data class Locale(
    /**
     * User IP address.
     */
    @SerialName("ip")
    val ip: String,
    /**
     * Country code in [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1) two-character format
     */
    @SerialName("countryCode")
    val countryCode: String,
    /**
     * Country name. This field support localization.
     */
    @SerialName("country")
    val country: String,
    /**
     * Continent code. A two character continent code &quot;AF&quot; for Africa, &quot;AN&quot; for Antarctica, &quot;AS&quot; for Asia, &quot;EU&quot; for Europe, &quot;NA&quot; for North America, &quot;OC&quot; for Oceania, and &quot;SA&quot; for South America.
     */
    @SerialName("continentCode")
    val continentCode: String,
    /**
     * Continent name. This field support localization.
     */
    @SerialName("continent")
    val continent: String,
    /**
     * True if country is part of the European Union.
     */
    @SerialName("eu")
    val eu: Boolean,
    /**
     * Currency code in [ISO 4217-1](http://en.wikipedia.org/wiki/ISO_4217) three-character format
     */
    @SerialName("currency")
    val currency: String,
)
