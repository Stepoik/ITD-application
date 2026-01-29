package com.itd.app.features.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetProfilePreviewResponse(
    val user: UserDto
)

@Serializable
data class UserDto(
    val avatar: String,
    val displayName: String,
    val id: String,
    val username: String,
    val verified: Boolean
)