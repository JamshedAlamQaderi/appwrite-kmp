package com.jamshedalamqaderi.appwrite.kmp.services

import com.jamshedalamqaderi.appwrite.kmp.Client
import com.jamshedalamqaderi.appwrite.kmp.Service
import com.jamshedalamqaderi.appwrite.kmp.models.ContinentList
import com.jamshedalamqaderi.appwrite.kmp.models.CountryList
import com.jamshedalamqaderi.appwrite.kmp.models.CurrencyList
import com.jamshedalamqaderi.appwrite.kmp.models.LanguageList
import com.jamshedalamqaderi.appwrite.kmp.models.Locale
import com.jamshedalamqaderi.appwrite.kmp.models.LocaleCodeList
import com.jamshedalamqaderi.appwrite.kmp.models.PhoneList
import io.ktor.http.HttpMethod

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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            Locale.serializer(),
            apiHeaders,
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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            LocaleCodeList.serializer(),
            apiHeaders,
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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            ContinentList.serializer(),
            apiHeaders,
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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            CountryList.serializer(),
            apiHeaders,
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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            CountryList.serializer(),
            apiHeaders,
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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            PhoneList.serializer(),
            apiHeaders,
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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            CurrencyList.serializer(),
            apiHeaders,
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

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            LanguageList.serializer(),
            apiHeaders,
        )
    }
}
