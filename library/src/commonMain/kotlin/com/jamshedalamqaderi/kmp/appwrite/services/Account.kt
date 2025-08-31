package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.enums.AuthenticationFactor
import com.jamshedalamqaderi.kmp.appwrite.enums.AuthenticatorType
import com.jamshedalamqaderi.kmp.appwrite.enums.OAuthProvider
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.extensions.mapSerializer
import com.jamshedalamqaderi.kmp.appwrite.models.IdentityList
import com.jamshedalamqaderi.kmp.appwrite.models.Jwt
import com.jamshedalamqaderi.kmp.appwrite.models.LogList
import com.jamshedalamqaderi.kmp.appwrite.models.MfaChallenge
import com.jamshedalamqaderi.kmp.appwrite.models.MfaFactors
import com.jamshedalamqaderi.kmp.appwrite.models.MfaRecoveryCodes
import com.jamshedalamqaderi.kmp.appwrite.models.MfaType
import com.jamshedalamqaderi.kmp.appwrite.models.Preferences
import com.jamshedalamqaderi.kmp.appwrite.models.Session
import com.jamshedalamqaderi.kmp.appwrite.models.SessionList
import com.jamshedalamqaderi.kmp.appwrite.models.Target
import com.jamshedalamqaderi.kmp.appwrite.models.Token
import com.jamshedalamqaderi.kmp.appwrite.models.User
import com.jamshedalamqaderi.kmp.appwrite.models.asPreferences
import com.jamshedalamqaderi.kmp.appwrite.models.asPreferencesUser
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.util.PlatformUtils
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmOverloads

/**
 * The Account service allows you to authenticate and manage a user account.
 **/
class Account(client: Client) : Service(client) {
    /**
     * Get account
     *
     * Get the currently logged in user.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> get(nestedType: KSerializer<T>): User<Preferences<T>> {
        val apiPath = "/account"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        val response =
            client.call(
                HttpMethod.Get,
                apiPath,
                apiHeaders,
                emptyMap(),
                converter = { it.body<User<JsonElement>>() },
            )
        return response.asPreferencesUser(nestedType)
    }

    /**
     * Get account
     *
     * Get the currently logged in user.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<Preferences<Map<String, String>>>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun get(): User<Preferences<Map<String, String>>> =
        get(
            nestedType = mapSerializer(),
        )

    /**
     * Create account
     *
     * Use this endpoint to allow a new user to register a new account in your project. After the user registration completes successfully, you can use the [/account/verfication](https://appwrite.io/docs/references/cloud/client-web/account#createVerification) route to start verifying the user email address. To allow the new user to login to their new account, you need to create a new [account session](https://appwrite.io/docs/references/cloud/client-web/account#createEmailSession).
     *
     * @param userId User ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param email User email.
     * @param password New user password. Must be between 8 and 256 chars.
     * @param name User name. Max length: 128 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @JvmOverloads
    suspend fun <T> create(
        userId: String,
        email: String,
        password: String,
        name: String? = null,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath = "/account"

        val apiParams =
            buildMap<String, Any> {
                put("userId", userId)
                put("email", email)
                put("password", password)
                if (name != null) put("name", name)
            }
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        val response =
            client.call(
                HttpMethod.Post,
                apiPath,
                apiHeaders,
                apiParams,
                converter = { it.body<User<JsonElement>>() },
            )
        return response.asPreferencesUser(nestedType)
    }

    /**
     * Create account
     *
     * Use this endpoint to allow a new user to register a new account in your project. After the user registration completes successfully, you can use the [/account/verfication](https://appwrite.io/docs/references/cloud/client-web/account#createVerification) route to start verifying the user email address. To allow the new user to login to their new account, you need to create a new [account session](https://appwrite.io/docs/references/cloud/client-web/account#createEmailSession).
     *
     * @param userId User ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param email User email.
     * @param password New user password. Must be between 8 and 256 chars.
     * @param name User name. Max length: 128 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun create(
        userId: String,
        email: String,
        password: String,
        name: String? = null,
    ): User<Preferences<Map<String, String>>> =
        create(
            userId,
            email,
            password,
            name,
            nestedType = mapSerializer(),
        )

    /**
     * Update email
     *
     * Update currently logged in user account email address. After changing user address, the user confirmation status will get reset. A new confirmation email is not sent automatically however you can use the send confirmation email endpoint again to send the confirmation email. For security measures, user password is required to complete this request.This endpoint can also be used to convert an anonymous account to a normal one, by passing an email address and a new password.
     *
     * @param email User email.
     * @param password User password. Must be at least 8 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> updateEmail(
        email: String,
        password: String,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath = "/account/email"

        val apiParams =
            mapOf(
                "email" to email,
                "password" to password,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        val response =
            client.call(
                HttpMethod.Patch,
                apiPath,
                apiHeaders,
                apiParams,
                converter = { it.body<User<JsonElement>>() },
            )
        return response.asPreferencesUser(nestedType)
    }

    /**
     * Update email
     *
     * Update currently logged in user account email address. After changing user address, the user confirmation status will get reset. A new confirmation email is not sent automatically however you can use the send confirmation email endpoint again to send the confirmation email. For security measures, user password is required to complete this request.This endpoint can also be used to convert an anonymous account to a normal one, by passing an email address and a new password.
     *
     * @param email User email.
     * @param password User password. Must be at least 8 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updateEmail(
        email: String,
        password: String,
    ): User<Preferences<Map<String, String>>> =
        updateEmail(
            email,
            password,
            nestedType = mapSerializer(),
        )

    /**
     * List Identities
     *
     * Get the list of identities for the currently logged in user.
     *
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long. You may filter on the following attributes: userId, provider, providerUid, providerEmail, providerAccessTokenExpiry
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.IdentityList]
     */
    @JvmOverloads
    suspend fun listIdentities(queries: List<String>? = null): IdentityList {
        val apiPath = "/account/identities"

        val apiParams =
            mapOf<String, Any>(
                "queries" to (queries ?: emptyList()),
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<IdentityList>() },
        )
    }

    /**
     * Delete identity
     *
     * Delete an identity by its unique ID.
     *
     * @param identityId Identity ID.
     * @return [Any]
     */
    suspend fun deleteIdentity(identityId: String) {
        val apiPath =
            "/account/identities/{identityId}"
                .replace("{identityId}", identityId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Delete,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = {},
        )
    }

    /**
     * Create JWT
     *
     * Use this endpoint to create a JSON Web Token. You can use the resulting JWT to authenticate on behalf of the current user when working with the Appwrite server-side API and SDKs. The JWT secret is valid for 15 minutes from its creation and will be invalid if the user will logout in that time frame.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Jwt]
     */
    suspend fun createJWT(): Jwt {
        val apiPath = "/account/jwt"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<Jwt>() },
        )
    }

    /**
     * List logs
     *
     * Get the list of latest security activity logs for the currently logged in user. Each log returns user IP address, location and date and time of log.
     *
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Only supported methods are limit and offset
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.LogList]
     */
    @JvmOverloads
    suspend fun listLogs(queries: List<String>? = null): LogList {
        val apiPath = "/account/logs"

        val apiParams =
            mapOf<String, Any>(
                "queries" to (queries ?: emptyList()),
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<LogList>() },
        )
    }

    /**
     * Update MFA
     *
     * Enable or disable MFA on an account.
     *
     * @param mfa Enable or disable MFA.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> updateMFA(
        mfa: Boolean,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath = "/account/mfa"

        val apiParams =
            mapOf<String, Any>(
                "mfa" to mfa,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<User<JsonElement>>() },
        ).asPreferencesUser(nestedType)
    }

    /**
     * Update MFA
     *
     * Enable or disable MFA on an account.
     *
     * @param mfa Enable or disable MFA.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updateMFA(mfa: Boolean): User<Preferences<JsonElement>> =
        updateMFA(
            mfa,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Add Authenticator
     *
     * Add an authenticator app to be used as an MFA factor. Verify the authenticator using the [verify authenticator](/docs/references/cloud/client-web/account#updateMfaAuthenticator) method.
     *
     * @param type Type of authenticator. Must be `totp`
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.MfaType]
     */
    suspend fun createMfaAuthenticator(type: AuthenticatorType): MfaType {
        val apiPath =
            "/account/mfa/authenticators/{type}"
                .replace("{type}", type.value)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<MfaType>() },
        )
    }

    /**
     * Verify Authenticator
     *
     * Verify an authenticator app after adding it using the [add authenticator](/docs/references/cloud/client-web/account#createMfaAuthenticator) method. add
     *
     * @param type Type of authenticator.
     * @param otp Valid verification token.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> updateMfaAuthenticator(
        type: AuthenticatorType,
        otp: String,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath =
            "/account/mfa/authenticators/{type}"
                .replace("{type}", type.value)

        val apiParams =
            mapOf<String, Any>(
                "otp" to otp,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<User<JsonElement>>() },
        ).asPreferencesUser(nestedType)
    }

    /**
     * Verify Authenticator
     *
     * Verify an authenticator app after adding it using the [add authenticator](/docs/references/cloud/client-web/account#createMfaAuthenticator) method. add
     *
     * @param type Type of authenticator.
     * @param otp Valid verification token.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updateMfaAuthenticator(
        type: AuthenticatorType,
        otp: String,
    ): User<Preferences<JsonElement>> =
        updateMfaAuthenticator(
            type,
            otp,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Delete Authenticator
     *
     * Delete an authenticator for a user by ID.
     *
     * @param type Type of authenticator.
     * @param otp Valid verification token.
     * @return [Any]
     */
    suspend fun deleteMfaAuthenticator(
        type: AuthenticatorType,
        otp: String,
    ) {
        val apiPath =
            "/account/mfa/authenticators/{type}"
                .replace("{type}", type.value)

        val apiParams =
            mapOf<String, Any>(
                "otp" to otp,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Delete,
            apiPath,
            apiHeaders,
            apiParams,
            converter = {},
        )
    }

    /**
     * Create 2FA Challenge
     *
     * Begin the process of MFA verification after sign-in. Finish the flow with [updateMfaChallenge](/docs/references/cloud/client-web/account#updateMfaChallenge) method.
     *
     * @param factor Factor used for verification. Must be one of following: `email`, `phone`, `totp`, `recoveryCode`.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.MfaChallenge]
     */
    suspend fun createMfaChallenge(factor: AuthenticationFactor): MfaChallenge {
        val apiPath = "/account/mfa/challenge"

        val apiParams =
            mapOf<String, Any>(
                "factor" to factor.value,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<MfaChallenge>() },
        )
    }

    /**
     * Create MFA Challenge (confirmation)
     *
     * Complete the MFA challenge by providing the one-time password. Finish the process of MFA verification by providing the one-time password. To begin the flow, use [createMfaChallenge](/docs/references/cloud/client-web/account#createMfaChallenge) method.
     *
     * @param challengeId ID of the challenge.
     * @param otp Valid verification token.
     * @return [Any]
     */
    suspend fun updateMfaChallenge(
        challengeId: String,
        otp: String,
    ) {
        val apiPath = "/account/mfa/challenge"

        val apiParams =
            mapOf<String, Any>(
                "challengeId" to challengeId,
                "otp" to otp,
            )

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = {},
        )
    }

    /**
     * List Factors
     *
     * List the factors available on the account to be used as a MFA challange.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.MfaFactors]
     */
    suspend fun listMfaFactors(): MfaFactors {
        val apiPath = "/account/mfa/factors"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<MfaFactors>() },
        )
    }

    /**
     * Get MFA Recovery Codes
     *
     * Get recovery codes that can be used as backup for MFA flow. Before getting codes, they must be generated using [createMfaRecoveryCodes](/docs/references/cloud/client-web/account#createMfaRecoveryCodes) method. An OTP challenge is required to read recovery codes.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.MfaRecoveryCodes]
     */
    suspend fun getMfaRecoveryCodes(): MfaRecoveryCodes {
        val apiPath = "/account/mfa/recovery-codes"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<MfaRecoveryCodes>() },
        )
    }

    /**
     * Create MFA Recovery Codes
     *
     * Generate recovery codes as backup for MFA flow. It&#039;s recommended to generate and show then immediately after user successfully adds their authehticator. Recovery codes can be used as a MFA verification type in [createMfaChallenge](/docs/references/cloud/client-web/account#createMfaChallenge) method.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.MfaRecoveryCodes]
     */
    suspend fun createMfaRecoveryCodes(): MfaRecoveryCodes {
        val apiPath = "/account/mfa/recovery-codes"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<MfaRecoveryCodes>() },
        )
    }

    /**
     * Regenerate MFA Recovery Codes
     *
     * Regenerate recovery codes that can be used as backup for MFA flow. Before regenerating codes, they must be first generated using [createMfaRecoveryCodes](/docs/references/cloud/client-web/account#createMfaRecoveryCodes) method. An OTP challenge is required to regenreate recovery codes.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.MfaRecoveryCodes]
     */
    suspend fun updateMfaRecoveryCodes(): MfaRecoveryCodes {
        val apiPath = "/account/mfa/recovery-codes"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<MfaRecoveryCodes>() },
        )
    }

    /**
     * Update name
     *
     * Update currently logged in user account name.
     *
     * @param name User name. Max length: 128 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> updateName(
        name: String,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath = "/account/name"

        val apiParams =
            mapOf<String, Any>(
                "name" to name,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<User<JsonElement>>() },
        ).asPreferencesUser(nestedType)
    }

    /**
     * Update name
     *
     * Update currently logged in user account name.
     *
     * @param name User name. Max length: 128 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updateName(name: String): User<Preferences<JsonElement>> =
        updateName(
            name,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Update password
     *
     * Update currently logged in user password. For validation, user is required to pass in the new password, and the old password. For users created with OAuth, Team Invites and Magic URL, oldPassword is optional.
     *
     * @param password New user password. Must be at least 8 chars.
     * @param oldPassword Current user password. Must be at least 8 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @JvmOverloads
    suspend fun <T> updatePassword(
        password: String,
        oldPassword: String? = null,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath = "/account/password"

        val apiParams =
            buildMap<String, Any> {
                put("password", password)
                if (oldPassword != null) {
                    put("oldPassword", oldPassword)
                }
            }
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<User<JsonElement>>() },
        ).asPreferencesUser(nestedType)
    }

    /**
     * Update password
     *
     * Update currently logged in user password. For validation, user is required to pass in the new password, and the old password. For users created with OAuth, Team Invites and Magic URL, oldPassword is optional.
     *
     * @param password New user password. Must be at least 8 chars.
     * @param oldPassword Current user password. Must be at least 8 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updatePassword(
        password: String,
        oldPassword: String? = null,
    ): User<Preferences<JsonElement>> =
        updatePassword(
            password,
            oldPassword,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Update phone
     *
     * Update the currently logged in user&#039;s phone number. After updating the phone number, the phone verification status will be reset. A confirmation SMS is not sent automatically, however you can use the [POST /account/verification/phone](https://appwrite.io/docs/references/cloud/client-web/account#createPhoneVerification) endpoint to send a confirmation SMS.
     *
     * @param phone Phone number. Format this number with a leading '+' and a country code, e.g., +16175551212.
     * @param password User password. Must be at least 8 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> updatePhone(
        phone: String,
        password: String,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath = "/account/phone"

        val apiParams =
            mapOf<String, Any>(
                "phone" to phone,
                "password" to password,
            )

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<User<JsonElement>>() },
        ).asPreferencesUser(nestedType)
    }

    /**
     * Update phone
     *
     * Update the currently logged in user&#039;s phone number. After updating the phone number, the phone verification status will be reset. A confirmation SMS is not sent automatically, however you can use the [POST /account/verification/phone](https://appwrite.io/docs/references/cloud/client-web/account#createPhoneVerification) endpoint to send a confirmation SMS.
     *
     * @param phone Phone number. Format this number with a leading '+' and a country code, e.g., +16175551212.
     * @param password User password. Must be at least 8 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updatePhone(
        phone: String,
        password: String,
    ): User<Preferences<JsonElement>> =
        updatePhone(
            phone,
            password,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Get account preferences
     *
     * Get the preferences as a key-value object for the currently logged in user.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Preferences<T>]
     */
    suspend fun <T> getPrefs(nestedType: KSerializer<T>): Preferences<T> {
        val apiPath = "/account/prefs"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<JsonElement>() },
        ).asPreferences(nestedType)
    }

    /**
     * Get account preferences
     *
     * Get the preferences as a key-value object for the currently logged in user.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Preferences<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun getPrefs(): Preferences<JsonElement> =
        getPrefs(
            nestedType = JsonElement.serializer(),
        )

    /**
     * Update preferences
     *
     * Update currently logged in user account preferences. The object you pass is stored as is, and replaces any previous value. The maximum allowed prefs size is 64kB and throws error if exceeded.
     *
     * @param prefs Prefs key-value JSON object.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> updatePrefs(
        prefs: Map<String, Any>,
        nestedType: KSerializer<T>,
    ): User<Preferences<T>> {
        val apiPath = "/account/prefs"

        val apiParams =
            mapOf<String, Any>(
                "prefs" to prefs,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<User<JsonElement>>() },
        ).asPreferencesUser(nestedType)
    }

    /**
     * Update preferences
     *
     * Update currently logged in user account preferences. The object you pass is stored as is, and replaces any previous value. The maximum allowed prefs size is 64kB and throws error if exceeded.
     *
     * @param prefs Prefs key-value JSON object.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updatePrefs(prefs: Map<String, Any>): User<Preferences<JsonElement>> =
        updatePrefs(
            prefs,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Create password recovery
     *
     * Sends the user an email with a temporary secret key for password reset. When the user clicks the confirmation link he is redirected back to your app password reset URL with the secret key and email address values attached to the URL query string. Use the query string params to submit a request to the [PUT /account/recovery](https://appwrite.io/docs/references/cloud/client-web/account#updateRecovery) endpoint to complete the process. The verification link sent to the user&#039;s email address is valid for 1 hour.
     *
     * @param email User email.
     * @param url URL to redirect the user back to your app from the recovery email. Only URLs from hostnames in your project platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    suspend fun createRecovery(
        email: String,
        url: String,
    ): Token {
        val apiPath = "/account/recovery"

        val apiParams =
            mapOf<String, Any>(
                "email" to email,
                "url" to url,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }

    /**
     * Create password recovery (confirmation)
     *
     * Use this endpoint to complete the user account password reset. Both the **userId** and **secret** arguments will be passed as query parameters to the redirect URL you have provided when sending your request to the [POST /account/recovery](https://appwrite.io/docs/references/cloud/client-web/account#createRecovery) endpoint.Please note that in order to avoid a [Redirect Attack](https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.md) the only valid redirect URLs are the ones from domains you have set when adding your platforms in the console interface.
     *
     * @param userId User ID.
     * @param secret Valid reset token.
     * @param password New user password. Must be between 8 and 256 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    suspend fun updateRecovery(
        userId: String,
        secret: String,
        password: String,
    ): Token {
        val apiPath = "/account/recovery"

        val apiParams =
            mapOf<String, Any>(
                "userId" to userId,
                "secret" to secret,
                "password" to password,
            )

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }

    /**
     * List sessions
     *
     * Get the list of active sessions across different devices for the currently logged in user.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.SessionList]
     */
    suspend fun listSessions(): SessionList {
        val apiPath = "/account/sessions"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<SessionList>() },
        )
    }

    /**
     * Delete sessions
     *
     * Delete all sessions from the user account and remove any sessions cookies from the end client.
     *
     * @return [Any]
     */
    suspend fun deleteSessions() {
        val apiPath = "/account/sessions"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Delete,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { },
        )
    }

    /**
     * Create anonymous session
     *
     * Use this endpoint to allow a new user to register an anonymous account in your project. This route will also create a new session for the user. To allow the new user to convert an anonymous account to a normal account, you need to update its [email and password](https://appwrite.io/docs/references/cloud/client-web/account#updateEmail) or create an [OAuth2 session](https://appwrite.io/docs/references/cloud/client-web/account#CreateOAuth2Session).
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Session]
     */
    suspend fun createAnonymousSession(): Session {
        val apiPath = "/account/sessions/anonymous"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<Session>() },
        )
    }

    /**
     * Create email password session
     *
     * Allow the user to login into their account by providing a valid email and password combination. This route will create a new session for the user.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param email User email.
     * @param password User password. Must be at least 8 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Session]
     */
    suspend fun createEmailPasswordSession(
        email: String,
        password: String,
    ): Session {
        val apiPath = "/account/sessions/email"

        val apiParams =
            mapOf<String, Any>(
                "email" to email,
                "password" to password,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Session>() },
        )
    }

    /**
     * Update magic URL session
     *
     * Use this endpoint to create a session from token. Provide the **userId** and **secret** parameters from the successful response of authentication flows initiated by token creation. For example, magic URL and phone login.
     *
     * @param userId User ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param secret Valid verification token.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Session]
     */
    suspend fun updateMagicURLSession(
        userId: String,
        secret: String,
    ): Session {
        val apiPath = "/account/sessions/magic-url"

        val apiParams =
            mapOf<String, Any>(
                "userId" to userId,
                "secret" to secret,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Session>() },
        )
    }

    /**
     * Create OAuth2 session
     *
     * Allow the user to login to their account using the OAuth2 provider of their choice. Each OAuth2 provider should be enabled from the Appwrite console first. Use the success and failure arguments to provide a redirect URL&#039;s back to your app when login is completed.If there is already an active session, the new session will be attached to the logged-in account. If there are no active sessions, the server will attempt to look for a user with the same email address as the email received from the OAuth2 provider and attach the new session to the existing user. If no matching user is found - the server will create a new user.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param provider OAuth2 Provider. Currently, supported providers are: amazon, apple, auth0, authentik, autodesk, bitbucket, bitly, box, dailymotion, discord, disqus, dropbox, etsy, facebook, github, gitlab, google, linkedin, microsoft, notion, oidc, okta, paypal, paypalSandbox, podio, salesforce, slack, spotify, stripe, tradeshift, tradeshiftBox, twitch, wordpress, yahoo, yammer, yandex, zoho, zoom.
     * @param success URL to redirect back to your app after a successful login attempt.  Only URLs from hostnames in your project's platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @param failure URL to redirect back to your app after a failed login attempt.  Only URLs from hostnames in your project's platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @param scopes A list of custom OAuth2 scopes. Check each provider internal docs for a list of supported scopes. Maximum of 100 scopes are allowed, each 4096 characters long.
     */
    suspend fun createOAuth2Session(
        provider: OAuthProvider,
        success: String? = null,
        failure: String? = null,
        scopes: List<String>? = null,
    ) {
        val apiPath =
            "/account/sessions/oauth2/{provider}"
                .replace("{provider}", provider.value)
        val apiParams =
            buildMap {
                success?.let { put("success", it) }
                failure?.let { put("failure", it) }
                scopes?.let { put("scopes", it) }
                client.config["project"]?.let { put("project", it) }
            }
        val apiQuery = mutableListOf<String>()
        apiParams.forEach {
            when (it.value) {
                is List<*> -> {
                    apiQuery.add("${it.key}[]=${it.value}")
                }

                else -> {
                    apiQuery.add("${it.key}=${it.value}")
                }
            }
        }

        val apiUrl = "${client.endpoint}$apiPath?${apiQuery.joinToString("&")}"
        val callbackUrlScheme = "appwrite-callback-${client.config["project"]}"
        val callbackUrl = launchOAuth2Url(apiUrl, callbackUrlScheme)
        if (callbackUrl.isEmpty() && PlatformUtils.IS_BROWSER) return
        val url = callbackUrl.let(::Url)
        val secret =
            url.parameters["secret"]
                ?: throw AppwriteException("OAuth2 response missing 'secret' parameter.")
        val userId =
            url.parameters["userId"]
                ?: throw AppwriteException("OAuth2 response missing 'userId' parameter.")

        createSession(userId, secret)
    }

    /**
     * Create OAuth2 session
     *
     * Allow the user to login to their account using the OAuth2 provider of their choice. Each OAuth2 provider should be enabled from the Appwrite console first. Use the success and failure arguments to provide a redirect URL&#039;s back to your app when login is completed.If there is already an active session, the new session will be attached to the logged-in account. If there are no active sessions, the server will attempt to look for a user with the same email address as the email received from the OAuth2 provider and attach the new session to the existing user. If no matching user is found - the server will create a new user.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param provider OAuth2 Provider. Currently, supported providers are: amazon, apple, auth0, authentik, autodesk, bitbucket, bitly, box, dailymotion, discord, disqus, dropbox, etsy, facebook, github, gitlab, google, linkedin, microsoft, notion, oidc, okta, paypal, paypalSandbox, podio, salesforce, slack, spotify, stripe, tradeshift, tradeshiftBox, twitch, wordpress, yahoo, yammer, yandex, zoho, zoom.
     * @param scopes A list of custom OAuth2 scopes. Check each provider internal docs for a list of supported scopes. Maximum of 100 scopes are allowed, each 4096 characters long.
     */
    @JvmOverloads
    suspend fun createOAuth2Session(
        provider: OAuthProvider,
        scopes: List<String>? = null,
    ) {
        val url = Url(client.endpoint)
        return createOAuth2Session(
            provider = provider,
            success = "appwrite-callback-${client.config["project"]}://${url.host}/auth/oauth2/success",
            failure = "appwrite-callback-${client.config["project"]}://${url.host}/auth/oauth2/failure",
            scopes = scopes,
        )
    }

    /**
     * Update phone session
     *
     * Use this endpoint to create a session from token. Provide the **userId** and **secret** parameters from the successful response of authentication flows initiated by token creation. For example, magic URL and phone login.
     *
     * @param userId User ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param secret Valid verification token.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Session]
     */
    suspend fun updatePhoneSession(
        userId: String,
        secret: String,
    ): Session {
        val apiPath = "/account/sessions/phone"

        val apiParams =
            mapOf<String, Any>(
                "userId" to userId,
                "secret" to secret,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Session>() },
        )
    }

    /**
     * Create session
     *
     * Use this endpoint to create a session from token. Provide the **userId** and **secret** parameters from the successful response of authentication flows initiated by token creation. For example, magic URL and phone login.
     *
     * @param userId User ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param secret Secret of a token generated by login methods. For example, the `createMagicURLToken` or `createPhoneToken` methods.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Session]
     */
    suspend fun createSession(
        userId: String,
        secret: String,
    ): Session {
        val apiPath = "/account/sessions/token"

        val apiParams =
            mapOf<String, Any>(
                "userId" to userId,
                "secret" to secret,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Session>() },
        )
    }

    /**
     * Get session
     *
     * Use this endpoint to get a logged in user&#039;s session using a Session ID. Inputting &#039;current&#039; will return the current session being used.
     *
     * @param sessionId Session ID. Use the string 'current' to get the current device session.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Session]
     */
    suspend fun getSession(sessionId: String): Session {
        val apiPath =
            "/account/sessions/{sessionId}"
                .replace("{sessionId}", sessionId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<Session>() },
        )
    }

    /**
     * Update session
     *
     * Use this endpoint to extend a session&#039;s length. Extending a session is useful when session expiry is short. If the session was created using an OAuth provider, this endpoint refreshes the access token from the provider.
     *
     * @param sessionId Session ID. Use the string 'current' to update the current device session.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Session]
     */
    suspend fun updateSession(sessionId: String): Session {
        val apiPath =
            "/account/sessions/{sessionId}"
                .replace("{sessionId}", sessionId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<Session>() },
        )
    }

    /**
     * Delete session
     *
     * Logout the user. Use &#039;current&#039; as the session ID to logout on this device, use a session ID to logout on another device. If you&#039;re looking to logout the user on all devices, use [Delete Sessions](https://appwrite.io/docs/references/cloud/client-web/account#deleteSessions) instead.
     *
     * @param sessionId Session ID. Use the string 'current' to delete the current device session.
     * @return [Any]
     */
    suspend fun deleteSession(sessionId: String) {
        val apiPath =
            "/account/sessions/{sessionId}"
                .replace("{sessionId}", sessionId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Delete,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = {},
        )
    }

    /**
     * Update status
     *
     * Block the currently logged-in user account. Behind the scene, the user record is not deleted but permanently blocked from any access. To completely delete a user, use the Users API instead.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    suspend fun <T> updateStatus(nestedType: KSerializer<T>): User<Preferences<T>> {
        val apiPath = "/account/status"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Patch,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<User<JsonElement>>() },
        ).asPreferencesUser(nestedType)
    }

    /**
     * Update status
     *
     * Block the currently logged in user account. Behind the scene, the user record is not deleted but permanently blocked from any access. To completely delete a user, use the Users API instead.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.User<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updateStatus(): User<Preferences<JsonElement>> =
        updateStatus(
            nestedType = JsonElement.serializer(),
        )

    /**
     * Create a push target
     *
     *
     *
     * @param targetId Target ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param identifier The target identifier (token, email, phone etc.)
     * @param providerId Provider ID. Message will be sent to this target from the specified provider ID. If no provider ID is set the first setup provider will be used.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Target]
     */
    @JvmOverloads
    suspend fun createPushTarget(
        targetId: String,
        identifier: String,
        providerId: String? = null,
    ): Target {
        val apiPath = "/account/targets/push"

        val apiParams =
            buildMap<String, Any> {
                put("targetId", targetId)
                put("identifier", identifier)
                providerId?.let { put("providerId", it) }
            }
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Target>() },
        )
    }

    /**
     * Update push target
     *
     *
     *
     * @param targetId Target ID.
     * @param identifier The target identifier (token, email, phone etc.)
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Target]
     */
    suspend fun updatePushTarget(
        targetId: String,
        identifier: String,
    ): Target {
        val apiPath =
            "/account/targets/{targetId}/push"
                .replace("{targetId}", targetId)

        val apiParams =
            mapOf<String, Any>(
                "identifier" to identifier,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Target>() },
        )
    }

    /**
     * Delete push target
     *
     *
     *
     * @param targetId Target ID.
     * @return [Unit]
     */
    suspend fun deletePushTarget(targetId: String) {
        val apiPath =
            "/account/targets/{targetId}/push"
                .replace("{targetId}", targetId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Delete,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = {},
        )
    }

    /**
     * Create an email token (OTP)
     *
     * Sends the user an email with a secret key for creating a session. If the provided user ID has not be registered, a new user will be created. Use the returned user ID and secret and submit a request to the [POST /v1/account/sessions/token](https://appwrite.io/docs/references/cloud/client-web/account#createSession) endpoint to complete the login process. The secret sent to the user&#039;s email is valid for 15 minutes.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param userId User ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param email User email.
     * @param phrase Toggle for a security phrase. If enabled, email will be send with a randomly generated phrase and the phrase will also be included in the response. Confirming phrases match increases the security of your authentication flow.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    @JvmOverloads
    suspend fun createEmailToken(
        userId: String,
        email: String,
        phrase: Boolean? = null,
    ): Token {
        val apiPath = "/account/tokens/email"

        val apiParams =
            buildMap {
                put("userId", userId)
                put("email", email)
                phrase?.let { put("phrase", it) }
            }
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }

    /**
     * Create magic URL token
     *
     * Sends the user an email with a secret key for creating a session. If the provided user ID has not been registered, a new user will be created. When the user clicks the link in the email, the user is redirected back to the URL you provided with the secret key and userId values attached to the URL query string. Use the query string parameters to submit a request to the [POST /v1/account/sessions/token](https://appwrite.io/docs/references/cloud/client-web/account#createSession) endpoint to complete the login process. The link sent to the user&#039;s email address is valid for 1 hour. If you are on a mobile device you can leave the URL parameter empty, so that the login completion will be handled by your Appwrite instance by default.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param userId Unique Id. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param email User email.
     * @param url URL to redirect the user back to your app from the magic URL login. Only URLs from hostnames in your project platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @param phrase Toggle for security phrase. If enabled, email will be send with a randomly generated phrase and the phrase will also be included in the response. Confirming phrases match increases the security of your authentication flow.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    @JvmOverloads
    suspend fun createMagicURLToken(
        userId: String,
        email: String,
        url: String? = null,
        phrase: Boolean? = null,
    ): Token {
        val apiPath = "/account/tokens/magic-url"

        val apiParams =
            buildMap {
                put("userId", userId)
                put("email", email)
                url?.let { put("url", it) }
                phrase?.let { put("phrase", it) }
            }
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }

    /**
     * Create OAuth2 token
     *
     * Allow the user to login to their account using the OAuth2 provider of their choice. Each OAuth2 provider should be enabled from the Appwrite console first. Use the success and failure arguments to provide a redirect URL&#039;s back to your app when login is completed. If authentication succeeds, `userId` and `secret` of a token will be appended to the success URL as query parameters. These can be used to create a new session using the [Create session](https://appwrite.io/docs/references/cloud/client-web/account#createSession) endpoint.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param provider OAuth2 Provider. Currently, supported providers are: amazon, apple, auth0, authentik, autodesk, bitbucket, bitly, box, dailymotion, discord, disqus, dropbox, etsy, facebook, github, gitlab, google, linkedin, microsoft, notion, oidc, okta, paypal, paypalSandbox, podio, salesforce, slack, spotify, stripe, tradeshift, tradeshiftBox, twitch, wordpress, yahoo, yammer, yandex, zoho, zoom.
     * @param success URL to redirect back to your app after a successful login attempt.  Only URLs from hostnames in your project's platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @param failure URL to redirect back to your app after a failed login attempt.  Only URLs from hostnames in your project's platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @param scopes A list of custom OAuth2 scopes. Check each provider internal docs for a list of supported scopes. Maximum of 100 scopes are allowed, each 4096 characters long.
     */
    suspend fun createOAuth2Token(
        provider: OAuthProvider,
        success: String? = null,
        failure: String? = null,
        scopes: List<String>? = null,
    ) {
        val apiPath =
            "/account/tokens/oauth2/{provider}"
                .replace("{provider}", provider.value)

        val apiParams =
            mapOf(
                "success" to success,
                "failure" to failure,
                "scopes" to scopes,
                "project" to client.config["project"],
            )
        val apiQuery = mutableListOf<String>()
        apiParams.forEach {
            when (it.value) {
                null -> {
                    return@forEach
                }

                is List<*> -> {
                    apiQuery.add("${it.key}[]=${it.value}")
                }

                else -> {
                    apiQuery.add("${it.key}=${it.value}")
                }
            }
        }
        val apiUrl = "${client.endpoint}$apiPath?${apiQuery.joinToString("&")}"
        val callbackUrlScheme = "appwrite-callback-${client.config["project"]}"
        val callbackUrl = launchOAuth2Url(apiUrl, callbackUrlScheme)
        if (callbackUrl.isEmpty() && PlatformUtils.IS_BROWSER) return
        val url = callbackUrl.let(::Url)
        val secret =
            url.parameters["secret"]
                ?: throw AppwriteException("OAuth2 response missing 'secret' parameter.")
        val userId =
            url.parameters["userId"]
                ?: throw AppwriteException("OAuth2 response missing 'userId' parameter.")

        createSession(userId, secret)
    }

    /**
     * Create OAuth2 token
     *
     * Allow the user to login to their account using the OAuth2 provider of their choice. Each OAuth2 provider should be enabled from the Appwrite console first. Use the success and failure arguments to provide a redirect URL&#039;s back to your app when login is completed. If authentication succeeds, `userId` and `secret` of a token will be appended to the success URL as query parameters. These can be used to create a new session using the [Create session](https://appwrite.io/docs/references/cloud/client-web/account#createSession) endpoint.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param provider OAuth2 Provider. Currently, supported providers are: amazon, apple, auth0, authentik, autodesk, bitbucket, bitly, box, dailymotion, discord, disqus, dropbox, etsy, facebook, github, gitlab, google, linkedin, microsoft, notion, oidc, okta, paypal, paypalSandbox, podio, salesforce, slack, spotify, stripe, tradeshift, tradeshiftBox, twitch, wordpress, yahoo, yammer, yandex, zoho, zoom.
     * @param scopes A list of custom OAuth2 scopes. Check each provider internal docs for a list of supported scopes. Maximum of 100 scopes are allowed, each 4096 characters long.
     */
    @JvmOverloads
    suspend fun createOAuth2Token(
        provider: OAuthProvider,
        scopes: List<String>? = null,
    ) {
        val url = Url(client.endpoint)
        return createOAuth2Token(
            provider = provider,
            success = "appwrite-callback-${client.config["project"]}://${url.host}/auth/oauth2/success",
            failure = "appwrite-callback-${client.config["project"]}://${url.host}/auth/oauth2/failure",
            scopes = scopes,
        )
    }

    /**
     * Create phone token
     *
     * Sends the user an SMS with a secret key for creating a session. If the provided user ID has not be registered, a new user will be created. Use the returned user ID and secret and submit a request to the [POST /v1/account/sessions/token](https://appwrite.io/docs/references/cloud/client-web/account#createSession) endpoint to complete the login process. The secret sent to the user&#039;s phone is valid for 15 minutes.A user is limited to 10 active sessions at a time by default. [Learn more about session limits](https://appwrite.io/docs/authentication-security#limits).
     *
     * @param userId Unique Id. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param phone Phone number. Format this number with a leading '+' and a country code, e.g., +16175551212.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    suspend fun createPhoneToken(
        userId: String,
        phone: String,
    ): Token {
        val apiPath = "/account/tokens/phone"

        val apiParams =
            mapOf<String, Any>(
                "userId" to userId,
                "phone" to phone,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }

    /**
     * Create email verification
     *
     * Use this endpoint to send a verification message to your user email address to confirm they are the valid owners of that address. Both the **userId** and **secret** arguments will be passed as query parameters to the URL you have provided to be attached to the verification email. The provided URL should redirect the user back to your app and allow you to complete the verification process by verifying both the **userId** and **secret** parameters. Learn more about how to [complete the verification process](https://appwrite.io/docs/references/cloud/client-web/account#updateVerification). The verification link sent to the user&#039;s email address is valid for 7 days.Please note that in order to avoid a [Redirect Attack](https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.md), the only valid redirect URLs are the ones from domains you have set when adding your platforms in the console interface.
     *
     * @param url URL to redirect the user back to your app from the verification email. Only URLs from hostnames in your project platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    suspend fun createVerification(url: String): Token {
        val apiPath = "/account/verification"

        val apiParams =
            mapOf<String, Any>(
                "url" to url,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }

    /**
     * Create email verification (confirmation)
     *
     * Use this endpoint to complete the user email verification process. Use both the **userId** and **secret** parameters that were attached to your app URL to verify the user email ownership. If confirmed this route will return a 200 status code.
     *
     * @param userId User ID.
     * @param secret Valid verification token.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    suspend fun updateVerification(
        userId: String,
        secret: String,
    ): Token {
        val apiPath = "/account/verification"

        val apiParams =
            mapOf<String, Any>(
                "userId" to userId,
                "secret" to secret,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }

    /**
     * Create phone verification
     *
     * Use this endpoint to send a verification SMS to the currently logged in user. This endpoint is meant for use after updating a user&#039;s phone number using the [accountUpdatePhone](https://appwrite.io/docs/references/cloud/client-web/account#updatePhone) endpoint. Learn more about how to [complete the verification process](https://appwrite.io/docs/references/cloud/client-web/account#updatePhoneVerification). The verification code sent to the user&#039;s phone number is valid for 15 minutes.
     *
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    suspend fun createPhoneVerification(): Token {
        val apiPath = "/account/verification/phone"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<Token>() },
        )
    }

    /**
     * Create phone verification (confirmation)
     *
     * Use this endpoint to complete the user phone verification process. Use the **userId** and **secret** that were sent to your user&#039;s phone number to verify the user email ownership. If confirmed this route will return a 200 status code.
     *
     * @param userId User ID.
     * @param secret Valid verification token.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Token]
     */
    suspend fun updatePhoneVerification(
        userId: String,
        secret: String,
    ): Token {
        val apiPath = "/account/verification/phone"

        val apiParams =
            mapOf<String, Any>(
                "userId" to userId,
                "secret" to secret,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<Token>() },
        )
    }
}

internal expect suspend fun launchOAuth2Url(
    authUrl: String,
    callbackScheme: String,
): String
