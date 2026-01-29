package com.itd.app.features.profile.api.models

import kotlinx.datetime.LocalDateTime

data class Profile(
    val avatar: String,
    val banner: String?,
    val bio: String?,
    val createdAt: LocalDateTime,
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
