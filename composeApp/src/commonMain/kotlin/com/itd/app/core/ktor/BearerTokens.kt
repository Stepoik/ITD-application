package com.itd.app.core.ktor

data class BearerTokens(
    val accessToken: String,
    val refreshToken: String
)
