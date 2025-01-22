package com.jamshedalamqaderi.appwrite.kmp.models

import kotlinx.serialization.Serializable

@Serializable
data class Progress(
    val total: Long,
    val completedBytes: Long,
)
