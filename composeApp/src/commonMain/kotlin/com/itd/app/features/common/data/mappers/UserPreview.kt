package com.itd.app.features.common.data.mappers

import com.itd.app.features.common.api.models.UserPreview
import com.itd.app.features.common.data.dto.UserPreviewDto

fun UserPreviewDto.toDomain(): UserPreview {
    return UserPreview(
        avatar = avatar,
        displayName = displayName,
        id = id,
        username = username,
        followersCount = followersCount,
        verified = verified
    )
}