package com.itd.app.features.profile.api.models

data class ProfilePreview(
    val avatar: String,
    val displayName: String,
    val id: String,
    val username: String,
    val verified: Boolean
)
