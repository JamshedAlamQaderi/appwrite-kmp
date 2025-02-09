package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Memberships List
 */
@Serializable
data class MembershipList(
    /**
     * Total number of memberships documents that matched your query.
     */
    @SerialName("total")
    val total: Long,
    /**
     * List of memberships.
     */
    @SerialName("memberships")
    val memberships: List<Membership>,
)
