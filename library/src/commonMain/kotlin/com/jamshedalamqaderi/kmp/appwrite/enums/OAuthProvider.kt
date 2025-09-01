package com.jamshedalamqaderi.kmp.appwrite.enums

import kotlinx.serialization.SerialName

enum class OAuthProvider(
    val value: String,
) {
    @SerialName("amazon")
    AMAZON("amazon"),

    @SerialName("apple")
    APPLE("apple"),

    @SerialName("auth0")
    AUTH0("auth0"),

    @SerialName("authentik")
    AUTHENTIK("authentik"),

    @SerialName("autodesk")
    AUTODESK("autodesk"),

    @SerialName("bitbucket")
    BITBUCKET("bitbucket"),

    @SerialName("bitly")
    BITLY("bitly"),

    @SerialName("box")
    BOX("box"),

    @SerialName("dailymotion")
    DAILYMOTION("dailymotion"),

    @SerialName("discord")
    DISCORD("discord"),

    @SerialName("disqus")
    DISQUS("disqus"),

    @SerialName("dropbox")
    DROPBOX("dropbox"),

    @SerialName("etsy")
    ETSY("etsy"),

    @SerialName("facebook")
    FACEBOOK("facebook"),

    @SerialName("github")
    GITHUB("github"),

    @SerialName("gitlab")
    GITLAB("gitlab"),

    @SerialName("google")
    GOOGLE("google"),

    @SerialName("linkedin")
    LINKEDIN("linkedin"),

    @SerialName("microsoft")
    MICROSOFT("microsoft"),

    @SerialName("notion")
    NOTION("notion"),

    @SerialName("oidc")
    OIDC("oidc"),

    @SerialName("okta")
    OKTA("okta"),

    @SerialName("paypal")
    PAYPAL("paypal"),

    @SerialName("paypalSandbox")
    PAYPALSANDBOX("paypalSandbox"),

    @SerialName("podio")
    PODIO("podio"),

    @SerialName("salesforce")
    SALESFORCE("salesforce"),

    @SerialName("slack")
    SLACK("slack"),

    @SerialName("spotify")
    SPOTIFY("spotify"),

    @SerialName("stripe")
    STRIPE("stripe"),

    @SerialName("tradeshift")
    TRADESHIFT("tradeshift"),

    @SerialName("tradeshiftBox")
    TRADESHIFTBOX("tradeshiftBox"),

    @SerialName("twitch")
    TWITCH("twitch"),

    @SerialName("wordpress")
    WORDPRESS("wordpress"),

    @SerialName("yahoo")
    YAHOO("yahoo"),

    @SerialName("yammer")
    YAMMER("yammer"),

    @SerialName("yandex")
    YANDEX("yandex"),

    @SerialName("zoho")
    ZOHO("zoho"),

    @SerialName("zoom")
    ZOOM("zoom"),

    @SerialName("mock")
    MOCK("mock"),
    ;

    override fun toString() = value
}
