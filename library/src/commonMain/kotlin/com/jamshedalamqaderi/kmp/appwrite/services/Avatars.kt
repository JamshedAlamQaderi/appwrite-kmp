package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.enums.Browser
import com.jamshedalamqaderi.kmp.appwrite.enums.CreditCard
import com.jamshedalamqaderi.kmp.appwrite.enums.Flag
import com.jamshedalamqaderi.kmp.appwrite.models.ClientParam
import io.ktor.http.HttpMethod
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlin.jvm.JvmOverloads

/**
 * The Avatars service aims to help you complete everyday tasks related to your app image, icons, and avatars.
 **/
class Avatars(client: Client) : Service(client) {
    /**
     * Get browser icon
     *
     * You can use this endpoint to show different browser icons to your users. The code argument receives the browser code as it appears in your user [GET /account/sessions](https://appwrite.io/docs/references/cloud/client-web/account#getSessions) endpoint. Use width, height and quality arguments to change the output settings.When one dimension is specified and the other is 0, the image is scaled with preserved aspect ratio. If both dimensions are 0, the API provides an image at source quality. If dimensions are not specified, the default size of image returned is 100x100px.
     *
     * @param code Browser Code.
     * @param width Image width. Pass an integer between 0 to 2000. Defaults to 100.
     * @param height Image height. Pass an integer between 0 to 2000. Defaults to 100.
     * @param quality Image quality. Pass an integer between 0 to 100. Defaults to 100.
     * @return [ByteArray]
     */
    @JvmOverloads
    suspend fun getBrowser(
        code: Browser,
        width: Long? = null,
        height: Long? = null,
        quality: Long? = null,
    ): ByteArray {
        val apiPath =
            "/avatars/browsers/{code}"
                .replace("{code}", code.value)

        val apiParams =
            listOf(
                ClientParam.StringParam("width", width?.toString()),
                ClientParam.StringParam("height", height?.toString()),
                ClientParam.StringParam("quality", quality?.toString()),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            deserializer = ByteArraySerializer(),
            params = apiParams,
        )
    }

    /**
     * Get credit card icon
     *
     * The credit card endpoint will return you the icon of the credit card provider you need. Use width, height and quality arguments to change the output settings.When one dimension is specified and the other is 0, the image is scaled with preserved aspect ratio. If both dimensions are 0, the API provides an image at source quality. If dimensions are not specified, the default size of image returned is 100x100px.
     *
     * @param code Credit Card Code. Possible values: amex, argencard, cabal, cencosud, diners, discover, elo, hipercard, jcb, mastercard, naranja, targeta-shopping, union-china-pay, visa, mir, maestro.
     * @param width Image width. Pass an integer between 0 to 2000. Defaults to 100.
     * @param height Image height. Pass an integer between 0 to 2000. Defaults to 100.
     * @param quality Image quality. Pass an integer between 0 to 100. Defaults to 100.
     * @return [ByteArray]
     */
    @JvmOverloads
    suspend fun getCreditCard(
        code: CreditCard,
        width: Long? = null,
        height: Long? = null,
        quality: Long? = null,
    ): ByteArray {
        val apiPath =
            "/avatars/credit-cards/{code}"
                .replace("{code}", code.value)

        val apiParams =
            listOf(
                ClientParam.StringParam("width", width?.toString()),
                ClientParam.StringParam("height", height?.toString()),
                ClientParam.StringParam("quality", quality?.toString()),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            deserializer = ByteArraySerializer(),
            params = apiParams,
        )
    }

    /**
     * Get favicon
     *
     * Use this endpoint to fetch the favorite icon (AKA favicon) of any remote website URL.This endpoint does not follow HTTP redirects.
     *
     * @param url Website URL which you want to fetch the favicon from.
     * @return [ByteArray]
     */
    suspend fun getFavicon(url: String): ByteArray {
        val apiPath = "/avatars/favicon"

        val apiParams =
            listOf(
                ClientParam.StringParam("url", url),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            deserializer = ByteArraySerializer(),
            params = apiParams,
        )
    }

    /**
     * Get country flag
     *
     * You can use this endpoint to show different country flags icons to your users. The code argument receives the 2 letter country code. Use width, height and quality arguments to change the output settings. Country codes follow the [ISO 3166-1](https://en.wikipedia.org/wiki/ISO_3166-1) standard.When one dimension is specified and the other is 0, the image is scaled with preserved aspect ratio. If both dimensions are 0, the API provides an image at source quality. If dimensions are not specified, the default size of image returned is 100x100px.
     *
     * @param code Country Code. ISO Alpha-2 country code format.
     * @param width Image width. Pass an integer between 0 to 2000. Defaults to 100.
     * @param height Image height. Pass an integer between 0 to 2000. Defaults to 100.
     * @param quality Image quality. Pass an integer between 0 to 100. Defaults to 100.
     * @return [ByteArray]
     */
    @JvmOverloads
    suspend fun getFlag(
        code: Flag,
        width: Long? = null,
        height: Long? = null,
        quality: Long? = null,
    ): ByteArray {
        val apiPath =
            "/avatars/flags/{code}"
                .replace("{code}", code.value)

        val apiParams =
            listOf(
                ClientParam.StringParam("width", width?.toString()),
                ClientParam.StringParam("height", height?.toString()),
                ClientParam.StringParam("quality", quality?.toString()),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            deserializer = ByteArraySerializer(),
            params = apiParams,
        )
    }

    /**
     * Get image from URL
     *
     * Use this endpoint to fetch a remote image URL and crop it to any image size you want. This endpoint is very useful if you need to crop and display remote images in your app or in case you want to make sure a 3rd party image is properly served using a TLS protocol.When one dimension is specified and the other is 0, the image is scaled with preserved aspect ratio. If both dimensions are 0, the API provides an image at source quality. If dimensions are not specified, the default size of image returned is 400x400px.This endpoint does not follow HTTP redirects.
     *
     * @param url Image URL which you want to crop.
     * @param width Resize preview image width, Pass an integer between 0 to 2000. Defaults to 400.
     * @param height Resize preview image height, Pass an integer between 0 to 2000. Defaults to 400.
     * @return [ByteArray]
     */
    @JvmOverloads
    suspend fun getImage(
        url: String,
        width: Long? = null,
        height: Long? = null,
    ): ByteArray {
        val apiPath = "/avatars/image"

        val apiParams =
            listOf(
                ClientParam.StringParam("url", url),
                ClientParam.StringParam("width", width?.toString()),
                ClientParam.StringParam("height", height?.toString()),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            deserializer = ByteArraySerializer(),
            params = apiParams,
        )
    }

    /**
     * Get user initials
     *
     * Use this endpoint to show your user initials avatar icon on your website or app. By default, this route will try to print your logged-in user name or email initials. You can also overwrite the user name if you pass the &#039;name&#039; parameter. If no name is given and no user is logged, an empty avatar will be returned.You can use the color and background params to change the avatar colors. By default, a random theme will be selected. The random theme will persist for the user&#039;s initials when reloading the same theme will always return for the same initials.When one dimension is specified and the other is 0, the image is scaled with preserved aspect ratio. If both dimensions are 0, the API provides an image at source quality. If dimensions are not specified, the default size of image returned is 100x100px.
     *
     * @param name Full Name. When empty, current user name or email will be used. Max length: 128 chars.
     * @param width Image width. Pass an integer between 0 to 2000. Defaults to 100.
     * @param height Image height. Pass an integer between 0 to 2000. Defaults to 100.
     * @param background Changes background color. By default a random color will be picked and stay will persistent to the given name.
     * @return [ByteArray]
     */
    @JvmOverloads
    suspend fun getInitials(
        name: String? = null,
        width: Long? = null,
        height: Long? = null,
        background: String? = null,
    ): ByteArray {
        val apiPath = "/avatars/initials"

        val apiParams =
            listOf(
                ClientParam.StringParam("name", name),
                ClientParam.StringParam("width", width?.toString()),
                ClientParam.StringParam("height", height?.toString()),
                ClientParam.StringParam("background", background),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            deserializer = ByteArraySerializer(),
            params = apiParams,
        )
    }

    /**
     * Get QR code
     *
     * Converts a given plain text to a QR code image. You can use the query parameters to change the size and style of the resulting image.
     *
     * @param text Plain text to be converted to QR code image.
     * @param size QR code size. Pass an integer between 1 to 1000. Defaults to 400.
     * @param margin Margin from edge. Pass an integer between 0 to 10. Defaults to 1.
     * @param download Return resulting image with 'Content-Disposition: attachment ' headers for the browser to start downloading it. Pass 0 for no header, or 1 for otherwise. Default value is set to 0.
     * @return [ByteArray]
     */
    @JvmOverloads
    suspend fun getQR(
        text: String,
        size: Long? = null,
        margin: Long? = null,
        download: Boolean? = null,
    ): ByteArray {
        val apiPath = "/avatars/qr"

        val apiParams =
            listOf(
                ClientParam.StringParam("text", text),
                ClientParam.StringParam("size", size?.toString()),
                ClientParam.StringParam("margin", margin?.toString()),
                ClientParam.StringParam("download", download?.toString()),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            deserializer = ByteArraySerializer(),
            params = apiParams,
        )
    }
}
