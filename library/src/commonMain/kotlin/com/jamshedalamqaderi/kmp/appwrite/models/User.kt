package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * User
 */
@OptIn(ExperimentalTime::class)
@Serializable
data class User<T>(
    /**
     * User ID.
     */
    @SerialName("\$id")
    val id: String,
    /**
     * User creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: Instant,
    /**
     * User update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: Instant,
    /**
     * User name.
     */
    @SerialName("name")
    val name: String,
    /**
     * Hashed user password.
     */
    @SerialName("password")
    var password: String? = null,
    /**
     * Password hashing algorithm.
     */
    @SerialName("hash")
    var hash: String? = null,
    /**
     * Password hashing algorithm configuration.
     */
    @SerialName("hashOptions")
    var hashOptions: String? = null,
    /**
     * User registration date in ISO 8601 format.
     */
    @SerialName("registration")
    val registration: String,
    /**
     * User status. Pass `true` for enabled and `false` for disabled.
     */
    @SerialName("status")
    val status: Boolean,
    /**
     * Labels for the user.
     */
    @SerialName("labels")
    val labels: List<String>,
    /**
     * Password update time in ISO 8601 format.
     */
    @SerialName("passwordUpdate")
    val passwordUpdate: String,
    /**
     * User email address.
     */
    @SerialName("email")
    val email: String,
    /**
     * User phone number in E.164 format.
     */
    @SerialName("phone")
    val phone: String,
    /**
     * Email verification status.
     */
    @SerialName("emailVerification")
    val emailVerification: Boolean,
    /**
     * Phone verification status.
     */
    @SerialName("phoneVerification")
    val phoneVerification: Boolean,
    /**
     * Multi factor authentication status.
     */
    @SerialName("mfa")
    val mfa: Boolean,
    /**
     * User preferences as a key-value object
     */
    @SerialName("prefs")
    val prefs: T,
    /**
     * A user-owned message receiver. A single user may have multiple e.g. emails, phones, and a browser. Each target is registered with a single provider.
     */
    @SerialName("targets")
    val targets: List<Target>,
    /**
     * Most recent access date in ISO 8601 format. This attribute is only updated again after 24 hours.
     */
    @SerialName("accessedAt")
    val accessedAt: String,
)

@OptIn(ExperimentalTime::class)
internal fun <T> User<JsonElement>.asPreferencesUser(deserializer: DeserializationStrategy<T>): User<Preferences<T>> =
    User(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
        name = name,
        password = password,
        hash = hash,
        hashOptions = hashOptions,
        registration = registration,
        status = status,
        labels = labels,
        passwordUpdate = passwordUpdate,
        email = email,
        phone = phone,
        emailVerification = emailVerification,
        phoneVerification = phoneVerification,
        mfa = mfa,
        prefs = this.prefs.asPreferences(deserializer),
        targets = targets,
        accessedAt = accessedAt,
    )
