package com.itd.app.features.search.ui.mappers

import com.itd.app.features.common.api.models.UserPreview
import com.itd.app.features.search.ui.UserVO

fun UserPreview.toVO(): UserVO {
    return UserVO(
        id = id,
        displayName = displayName,
        username = username,
        followersCount = followersCount.toString(),
        avatar = avatar,
        verified = verified
    )
}