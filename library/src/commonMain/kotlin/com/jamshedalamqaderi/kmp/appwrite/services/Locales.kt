package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.models.ContinentList
import com.jamshedalamqaderi.kmp.appwrite.models.CountryList
import com.jamshedalamqaderi.kmp.appwrite.models.CurrencyList
import com.jamshedalamqaderi.kmp.appwrite.models.LanguageList
import com.jamshedalamqaderi.kmp.appwrite.models.Locale
import com.jamshedalamqaderi.kmp.appwrite.models.LocaleCodeList
import com.jamshedalamqaderi.kmp.appwrite.models.PhoneList
import io.ktor.http.HttpMethod
import io.ktor.client.call.*

/**
 * The Locale service allows you to customize your app based on your users&#039; location.
 **/
class Locales(client: Client) : Service(client) {
    /**
     * Get user locale
     *
     * Get the current user location based on IP. Returns an object with user country code, country name, continent name, continent code, ip address and suggested currency. You can use the locale header to get the data in a supported language.([IP Geolocation by DB-IP](https://db-ip.com))
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.Locale]
     */
    suspend fun get(): Locale {
        val apiPath = "/locale"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )
        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<Locale>() }
        )
    }

    /**
     * List Locale Codes
     *
     * List of all locale codes in [ISO 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes).
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.LocaleCodeList]
     */
    suspend fun listCodes(): LocaleCodeList {
        val apiPath = "/locale/codes"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )
        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<LocaleCodeList>() }
        )
    }

    /**
     * List continents
     *
     * List of all continents. You can use the locale header to get the data in a supported language.
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.ContinentList]
     */
    suspend fun listContinents(): ContinentList {
        val apiPath = "/locale/continents"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )
        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<ContinentList>() }
        )
    }

    /**
     * List countries
     *
     * List of all countries. You can use the locale header to get the data in a supported language.
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.CountryList]
     */
    suspend fun listCountries(): CountryList {
        val apiPath = "/locale/countries"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )
        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<CountryList>() }
        )
    }

    /**
     * List EU countries
     *
     * List of all countries that are currently members of the EU. You can use the locale header to get the data in a supported language.
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.CountryList]
     */
    suspend fun listCountriesEU(): CountryList {
        val apiPath = "/locale/countries/eu"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<CountryList>() }
        )
    }

    /**
     * List countries phone codes
     *
     * List of all countries phone codes. You can use the locale header to get the data in a supported language.
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.PhoneList]
     */
    suspend fun listCountriesPhones(): PhoneList {
        val apiPath = "/locale/countries/phones"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<PhoneList>() }
        )
    }

    /**
     * List currencies
     *
     * List of all currencies, including currency symbol, name, plural, and decimal digits for all major and minor currencies. You can use the locale header to get the data in a supported language.
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.CurrencyList]
     */
    suspend fun listCurrencies(): CurrencyList {
        val apiPath = "/locale/currencies"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<CurrencyList>() }
        )
    }

    /**
     * List languages
     *
     * List of all languages classified by ISO 639-1 including 2-letter code, name in English, and name in the respective language.
     *
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.LanguageList]
     */
    suspend fun listLanguages(): LanguageList {
        val apiPath = "/locale/languages"

        val apiHeaders = mapOf(
            "content-type" to "application/json",
        )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<LanguageList>() }
        )
    }
}
