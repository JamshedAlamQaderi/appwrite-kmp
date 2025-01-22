package com.jamshedalamqaderi.appwrite.kmp.enums

import kotlinx.serialization.SerialName

enum class ImageFormat(private val value: String) {
    @SerialName("jpg")
    JPG("jpg"),

    @SerialName("jpeg")
    JPEG("jpeg"),

    @SerialName("gif")
    GIF("gif"),

    @SerialName("png")
    PNG("png"),

    @SerialName("webp")
    WEBP("webp"),
    ;

    override fun toString() = value
}
