package com.itd.app.features.profile.data.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class ProfileDto(
    val avatar: String,
    val banner: String?,
    val bio: String?,
    val createdAt: Instant,
    val displayName: String,
    val followersCount: Int,
    val followingCount: Int,
    val id: String,
    val isFollowedBy: Boolean,
    val isFollowing: Boolean,
    val pinnedPostId: String?,
    val postsCount: Int,
    val username: String,
    val verified: Boolean,
    val wallClosed: Boolean
)
