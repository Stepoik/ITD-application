package com.itd.app.features.profile.ui.components.profileInfo.mappers

import com.itd.app.features.profile.api.models.Profile
import com.itd.app.features.profile.ui.components.profileInfo.ProfileInfo

fun Profile.toInfo(): ProfileInfo {
    return ProfileInfo(
        avatar = avatar,
        displayName = displayName,
        username = "@$username",
        verified = verified,
        banner = banner,
        bio = bio,
        createdAt = createdAt,
        followersCount = followersCount,
        followingCount = followingCount,
        isFollowedBy = isFollowedBy,
        isFollowing = isFollowing,
        wallClosed = wallClosed
    )
}