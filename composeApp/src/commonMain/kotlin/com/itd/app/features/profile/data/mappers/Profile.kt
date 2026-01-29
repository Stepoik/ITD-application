package com.itd.app.features.profile.data.mappers

import com.itd.app.features.profile.api.models.Profile
import com.itd.app.features.profile.api.models.ProfilePreview
import com.itd.app.features.profile.data.dto.ProfileDto
import com.itd.app.features.profile.data.dto.UserDto
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun UserDto.toProfile(): ProfilePreview {
    return ProfilePreview(
        avatar = avatar,
        displayName = displayName,
        id = id,
        username = username,
        verified = verified
    )
}

fun ProfileDto.toProfile(): Profile {
    return Profile(
        avatar = avatar,
        displayName = displayName,
        id = id,
        username = username,
        verified = verified,
        banner = banner,
        bio = bio,
        createdAt = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()),
        followersCount = followersCount,
        followingCount = followingCount,
        isFollowedBy = isFollowedBy,
        isFollowing = isFollowing,
        pinnedPostId = pinnedPostId,
        postsCount = postsCount,
        wallClosed = wallClosed
    )
}