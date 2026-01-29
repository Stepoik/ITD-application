package com.itd.app.features.common.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetWhoToFollowResponse(
    val users: List<UserPreviewDto>
)

@Serializable
data class UserPreviewDto(
    val id: String,
    val avatar: String,
    val displayName: String,
    val followersCount: Int,
    val username: String,
    val verified: Boolean
)