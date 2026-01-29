package com.itd.app.features.profile.ui.components.profileInfo

import com.itd.app.core.decompose.UIState
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoState(
    val isLoading: Boolean = false,
    val info: ProfileInfo? = null
): UIState

@Serializable
data class ProfileInfo(
    val displayName: String,
    val avatar: String,
    val bio: String?,
    val createdAt: LocalDateTime,
    val banner: String?,
    val username: String,
    val isFollowing: Boolean,
    val isFollowedBy: Boolean,
    val followersCount: Int,
    val followingCount: Int,
    val verified: Boolean,
    val wallClosed: Boolean
)