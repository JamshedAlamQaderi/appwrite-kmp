package com.jamshedalamqaderi.appwrite.kmp.enums

import kotlinx.serialization.SerialName

enum class ImageGravity(val value: String) {
    @SerialName("center")
    CENTER("center"),

    @SerialName("top-left")
    TOP_LEFT("top-left"),

    @SerialName("top")
    TOP("top"),

    @SerialName("top-right")
    TOP_RIGHT("top-right"),

    @SerialName("left")
    LEFT("left"),

    @SerialName("right")
    RIGHT("right"),

    @SerialName("bottom-left")
    BOTTOM_LEFT("bottom-left"),

    @SerialName("bottom")
    BOTTOM("bottom"),

    @SerialName("bottom-right")
    BOTTOM_RIGHT("bottom-right"),
    ;

    override fun toString() = value
}
