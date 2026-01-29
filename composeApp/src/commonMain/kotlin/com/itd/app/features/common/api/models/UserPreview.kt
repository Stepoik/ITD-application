package com.itd.app.features.common.api.models

data class UserPreview(
    val avatar: String,
    val displayName: String,
    val id: String,
    val username: String,
    val followersCount: Int,
    val verified: Boolean
)
