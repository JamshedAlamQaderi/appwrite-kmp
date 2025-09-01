package com.jamshedalamqaderi.kmp.appwrite.enums

import kotlinx.serialization.SerialName

enum class ImageFormat(
    val value: String,
) {
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
