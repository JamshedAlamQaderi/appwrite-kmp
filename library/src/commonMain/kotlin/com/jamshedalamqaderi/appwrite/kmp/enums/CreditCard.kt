package com.jamshedalamqaderi.appwrite.kmp.enums

import kotlinx.serialization.SerialName

enum class CreditCard(private val value: String) {
    @SerialName("amex")
    AMERICAN_EXPRESS("amex"),

    @SerialName("argencard")
    ARGENCARD("argencard"),

    @SerialName("cabal")
    CABAL("cabal"),

    @SerialName("cencosud")
    CENCOSUD("cencosud"),

    @SerialName("diners")
    DINERS_CLUB("diners"),

    @SerialName("discover")
    DISCOVER("discover"),

    @SerialName("elo")
    ELO("elo"),

    @SerialName("hipercard")
    HIPERCARD("hipercard"),

    @SerialName("jcb")
    JCB("jcb"),

    @SerialName("mastercard")
    MASTERCARD("mastercard"),

    @SerialName("naranja")
    NARANJA("naranja"),

    @SerialName("targeta-shopping")
    TARJETA_SHOPPING("targeta-shopping"),

    @SerialName("union-china-pay")
    UNION_CHINA_PAY("union-china-pay"),

    @SerialName("visa")
    VISA("visa"),

    @SerialName("mir")
    MIR("mir"),

    @SerialName("maestro")
    MAESTRO("maestro"),
    ;

    override fun toString() = value
}
