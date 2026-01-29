package com.itd.app.features.feed.api.models

data class Author(
    val avatar: String,
    val displayName: String,
    val id: String,
    val username: String,
    val verified: Boolean
)
