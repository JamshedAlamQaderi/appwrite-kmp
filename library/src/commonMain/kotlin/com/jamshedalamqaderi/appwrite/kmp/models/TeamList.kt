package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Teams List
 */
@Serializable
data class TeamList<T>(
    /**
     * Total number of teams documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of teams.
     */
    @SerialName("teams")
    val teams: List<Team<T>>,
)
