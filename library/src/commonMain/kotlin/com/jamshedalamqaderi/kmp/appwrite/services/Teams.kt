package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.exceptions.AppwriteException
import com.jamshedalamqaderi.kmp.appwrite.models.Membership
import com.jamshedalamqaderi.kmp.appwrite.models.MembershipList
import com.jamshedalamqaderi.kmp.appwrite.models.Preferences
import com.jamshedalamqaderi.kmp.appwrite.models.Team
import com.jamshedalamqaderi.kmp.appwrite.models.TeamList
import com.jamshedalamqaderi.kmp.appwrite.models.asPreferences
import com.jamshedalamqaderi.kmp.appwrite.models.asTeamPreferences
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmOverloads

/**
 * The Teams service allows you to group users of your project and to enable them to share read and write access to your project resources
 **/
class Teams(
    client: Client,
) : Service(client) {
    /**
     * List teams
     *
     * Get a list of all the teams in which the current user is a member. You can use the parameters to filter your results.
     *
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long. You may filter on the following attributes: name, total, billingPlan
     * @param search Search term to filter your list results. Max length: 256 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.TeamList<T>]
     */
    @JvmOverloads
    suspend fun <T> list(
        queries: List<String>? = null,
        search: String? = null,
        nestedType: KSerializer<T>,
    ): TeamList<Preferences<T>> {
        val apiPath = "/teams"

        val apiParams =
            buildMap {
                put("queries", queries ?: emptyList<String>())
                if (search != null) put("search", search)
            }
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        val teamList =
            client.call(
                HttpMethod.Get,
                apiPath,
                apiHeaders,
                apiParams,
                converter = { it.body<TeamList<JsonElement>>() },
            )
        return TeamList(
            total = teamList.total,
            teams = teamList.teams.map { it.asTeamPreferences(nestedType) },
        )
    }

    /**
     * List teams
     *
     * Get a list of all the teams in which the current user is a member. You can use the parameters to filter your results.
     *
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long. You may filter on the following attributes: name, total, billingPlan
     * @param search Search term to filter your list results. Max length: 256 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.TeamList<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun list(
        queries: List<String>? = null,
        search: String? = null,
    ): TeamList<Preferences<JsonElement>> =
        list(
            queries,
            search,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Create team
     *
     * Create a new team. The user who creates the team will automatically be assigned as the owner of the team. Only the users with the owner role can invite new members, add new owners and delete or update the team.
     *
     * @param teamId Team ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param name Team name. Max length: 128 chars.
     * @param roles Array of strings. Use this param to set the roles in the team for the user who created it. The default role is **owner**. A role can be any string. Learn more about [roles and permissions](https://appwrite.io/docs/permissions). Maximum of 100 roles are allowed, each 32 characters long.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Team<T>]
     */
    @JvmOverloads
    suspend fun <T> create(
        teamId: String,
        name: String,
        roles: List<String>? = null,
        nestedType: KSerializer<T>,
    ): Team<Preferences<T>> {
        val apiPath = "/teams"

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        val params =
            buildMap<String, Any> {
                put("teamId", teamId)
                put("name", name)
                put("roles", roles ?: emptyList<String>())
            }
        return client
            .call(
                HttpMethod.Post,
                apiPath,
                apiHeaders,
                params,
                converter = { it.body<Team<JsonElement>>() },
            ).asTeamPreferences(nestedType)
    }

    /**
     * Create team
     *
     * Create a new team. The user who creates the team will automatically be assigned as the owner of the team. Only the users with the owner role can invite new members, add new owners and delete or update the team.
     *
     * @param teamId Team ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param name Team name. Max length: 128 chars.
     * @param roles Array of strings. Use this param to set the roles in the team for the user who created it. The default role is **owner**. A role can be any string. Learn more about [roles and permissions](https://appwrite.io/docs/permissions). Maximum of 100 roles are allowed, each 32 characters long.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Team<T>]
     */
    @JvmOverloads
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun create(
        teamId: String,
        name: String,
        roles: List<String>? = null,
    ): Team<Preferences<JsonElement>> =
        create(
            teamId,
            name,
            roles,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Get team
     *
     * Get a team by its ID. All team members have read access for this resource.
     *
     * @param teamId Team ID.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Team<T>]
     */
    suspend fun <T> get(
        teamId: String,
        nestedType: KSerializer<T>,
    ): Team<Preferences<T>> {
        val apiPath =
            "/teams/{teamId}"
                .replace("{teamId}", teamId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client
            .call(
                HttpMethod.Get,
                apiPath,
                apiHeaders,
                emptyMap(),
                converter = { it.body<Team<JsonElement>>() },
            ).asTeamPreferences(nestedType)
    }

    /**
     * Get team
     *
     * Get a team by its ID. All team members have read access for this resource.
     *
     * @param teamId Team ID.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Team<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun get(teamId: String): Team<Preferences<JsonElement>> =
        get(
            teamId,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Update name
     *
     * Update the team&#039;s name by its unique ID.
     *
     * @param teamId Team ID.
     * @param name New team name. Max length: 128 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Team<T>]
     */
    suspend fun <T> updateName(
        teamId: String,
        name: String,
        nestedType: KSerializer<T>,
    ): Team<Preferences<T>> {
        val apiPath =
            "/teams/{teamId}"
                .replace("{teamId}", teamId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        val apiParams =
            mapOf(
                "name" to name,
            )
        return client
            .call(
                HttpMethod.Put,
                apiPath,
                apiHeaders,
                apiParams,
                converter = { it.body<Team<JsonElement>>() },
            ).asTeamPreferences(nestedType)
    }

    /**
     * Update name
     *
     * Update the team&#039;s name by its unique ID.
     *
     * @param teamId Team ID.
     * @param name New team name. Max length: 128 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Team<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updateName(
        teamId: String,
        name: String,
    ): Team<Preferences<JsonElement>> =
        updateName(
            teamId,
            name,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Delete team
     *
     * Delete a team using its ID. Only team members with the owner role can delete the team.
     *
     * @param teamId Team ID.
     * @return [Any]
     */
    suspend fun delete(teamId: String) {
        val apiPath =
            "/teams/{teamId}"
                .replace("{teamId}", teamId)

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
     * List team memberships
     *
     * Use this endpoint to list a team&#039;s members using the team&#039;s ID. All team members have read access to this endpoint.
     *
     * @param teamId Team ID.
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long. You may filter on the following attributes: userId, teamId, invited, joined, confirm
     * @param search Search term to filter your list results. Max length: 256 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.MembershipList]
     */
    @JvmOverloads
    suspend fun listMemberships(
        teamId: String,
        queries: List<String>? = null,
        search: String? = null,
    ): MembershipList {
        val apiPath =
            "/teams/{teamId}/memberships"
                .replace("{teamId}", teamId)

        val apiParams =
            buildMap {
                put("queries", queries ?: emptyList<String>())
                if (search != null) put("search", search)
            }
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<MembershipList>() },
        )
    }

    /**
     * Create team membership
     *
     * Invite a new member to join your team. Provide an ID for existing users, or invite unregistered users using an email or phone number. If initiated from a Client SDK, Appwrite will send an email or sms with a link to join the team to the invited user, and an account will be created for them if one doesn&#039;t exist. If initiated from a Server SDK, the new member will be added automatically to the team.You only need to provide one of a user ID, email, or phone number. Appwrite will prioritize accepting the user ID &gt; email &gt; phone number if you provide more than one of these parameters.Use the `url` parameter to redirect the user from the invitation email to your app. After the user is redirected, use the [Update Team Membership Status](https://appwrite.io/docs/references/cloud/client-web/teams#updateMembershipStatus) endpoint to allow the user to accept the invitation to the team. Please note that to avoid a [Redirect Attack](https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.md) Appwrite will accept the only redirect URLs under the domains you have added as a platform on the Appwrite Console.
     *
     * @param teamId Team ID.
     * @param roles Array of strings. Use this param to set the user roles in the team. A role can be any string. Learn more about [roles and permissions](https://appwrite.io/docs/permissions). Maximum of 100 roles are allowed, each 32 characters long.
     * @param email Email of the new team member.
     * @param userId ID of the user to be added to a team.
     * @param phone Phone number. Format this number with a leading '+' and a country code, e.g., +16175551212.
     * @param url URL to redirect the user back to your app from the invitation email. This parameter is not required when an API key is supplied. Only URLs from hostnames in your project platform list are allowed. This requirement helps to prevent an [open redirect](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) attack against your project API.
     * @param name Name of the new team member. Max length: 128 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Membership]
     */
    @JvmOverloads
    suspend fun createMembership(
        teamId: String,
        roles: List<String>,
        email: String? = null,
        userId: String? = null,
        phone: String? = null,
        url: String? = null,
        name: String? = null,
    ): Membership {
        val apiPath =
            "/teams/{teamId}/memberships"
                .replace("{teamId}", teamId)

        val apiParams =
            buildMap<String, Any> {
                if (email != null) put("email", email)
                if (userId != null) put("userId", userId)
                if (phone != null) put("phone", phone)
                put("roles", roles)
                if (url != null) put("url", url)
                if (name != null) put("name", name)
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
            converter = { it.body<Membership>() },
        )
    }

    /**
     * Get team membership
     *
     * Get a team member by the membership unique id. All team members have read access for this resource.
     *
     * @param teamId Team ID.
     * @param membershipId Membership ID.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Membership]
     */
    suspend fun getMembership(
        teamId: String,
        membershipId: String,
    ): Membership {
        val apiPath =
            "/teams/{teamId}/memberships/{membershipId}"
                .replace("{teamId}", teamId)
                .replace("{membershipId}", membershipId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            emptyMap(),
            converter = { it.body<Membership>() },
        )
    }

    /**
     * Update membership
     *
     * Modify the roles of a team member. Only team members with the owner role have access to this endpoint. Learn more about [roles and permissions](https://appwrite.io/docs/permissions).
     *
     * @param teamId Team ID.
     * @param membershipId Membership ID.
     * @param roles An array of strings. Use this param to set the user's roles in the team. A role can be any string. Learn more about [roles and permissions](https://appwrite.io/docs/permissions). Maximum of 100 roles are allowed, each 32 characters long.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Membership]
     */
    suspend fun updateMembership(
        teamId: String,
        membershipId: String,
        roles: List<String>,
    ): Membership {
        val apiPath =
            "/teams/{teamId}/memberships/{membershipId}"
                .replace("{teamId}", teamId)
                .replace("{membershipId}", membershipId)

        val apiParams =
            mapOf(
                "roles" to roles,
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
            converter = { it.body<Membership>() },
        )
    }

    /**
     * Delete team membership
     *
     * This endpoint allows a user to leave a team or for a team owner to delete the membership of any other team member. You can also use this endpoint to delete a user membership even if it is not accepted.
     *
     * @param teamId Team ID.
     * @param membershipId Membership ID.
     * @return [JsonElement]
     */
    suspend fun deleteMembership(
        teamId: String,
        membershipId: String,
    ) {
        val apiPath =
            "/teams/{teamId}/memberships/{membershipId}"
                .replace("{teamId}", teamId)
                .replace("{membershipId}", membershipId)

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
     * Update team membership status
     *
     * Use this endpoint to allow a user to accept an invitation to join a team after being redirected back to your app from the invitation email received by the user.If the request is successful, a session for the user is automatically created.
     *
     * @param teamId Team ID.
     * @param membershipId Membership ID.
     * @param userId User ID.
     * @param secret Secret key.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Membership]
     */
    suspend fun updateMembershipStatus(
        teamId: String,
        membershipId: String,
        userId: String,
        secret: String,
    ): Membership {
        val apiPath =
            "/teams/{teamId}/memberships/{membershipId}/status"
                .replace("{teamId}", teamId)
                .replace("{membershipId}", membershipId)

        val apiParams =
            mapOf(
                "userId" to userId,
                "secret" to secret,
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
            converter = { it.body<Membership>() },
        )
    }

    /**
     * Get team preferences
     *
     * Get the team&#039;s shared preferences by its unique ID. If a preference doesn&#039;t need to be shared by all team members, prefer storing them in [user preferences](https://appwrite.io/docs/references/cloud/client-web/account#getPrefs).
     *
     * @param teamId Team ID.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Preferences<T>]
     */
    suspend fun <T> getPrefs(
        teamId: String,
        nestedType: KSerializer<T>,
    ): Preferences<T> {
        val apiPath =
            "/teams/{teamId}/prefs"
                .replace("{teamId}", teamId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client
            .call(
                HttpMethod.Get,
                apiPath,
                apiHeaders,
                emptyMap(),
                converter = { it.body<JsonElement>() },
            ).asPreferences(nestedType)
    }

    /**
     * Get team preferences
     *
     * Get the team&#039;s shared preferences by its unique ID. If a preference doesn&#039;t need to be shared by all team members, prefer storing them in [user preferences](https://appwrite.io/docs/references/cloud/client-web/account#getPrefs).
     *
     * @param teamId Team ID.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Preferences<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun getPrefs(teamId: String): Preferences<JsonElement> =
        getPrefs(
            teamId,
            nestedType = JsonElement.serializer(),
        )

    /**
     * Update preferences
     *
     * Update the team&#039;s preferences by its unique ID. The object you pass is stored as is and replaces any previous value. The maximum allowed prefs size is 64kB and throws an error if exceeded.
     *
     * @param teamId Team ID.
     * @param prefs Prefs key-value JSON object.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Preferences<T>]
     */
    suspend fun <T> updatePrefs(
        teamId: String,
        prefs: Map<String, String>,
        nestedType: KSerializer<T>,
    ): Preferences<T> {
        val apiPath =
            "/teams/{teamId}/prefs"
                .replace("{teamId}", teamId)

        val apiParams =
            mapOf(
                "prefs" to prefs,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client
            .call(
                HttpMethod.Put,
                apiPath,
                apiHeaders,
                apiParams,
                converter = { it.body<JsonElement>() },
            ).asPreferences(nestedType)
    }

    /**
     * Update preferences
     *
     * Update the team&#039;s preferences by its unique ID. The object you pass is stored as is and replaces any previous value. The maximum allowed prefs size is 64kB and throws an error if exceeded.
     *
     * @param teamId Team ID.
     * @param prefs Prefs key-value JSON object.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.Preferences<T>]
     */
    @Throws(AppwriteException::class, CancellationException::class)
    suspend fun updatePrefs(
        teamId: String,
        prefs: Map<String, String>,
    ): Preferences<JsonElement> =
        updatePrefs(
            teamId,
            prefs,
            nestedType = JsonElement.serializer(),
        )
}
