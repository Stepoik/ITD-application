package com.itd.app.features.feed.data.mappers

import com.itd.app.features.feed.api.models.LikeStatus
import com.itd.app.features.feed.data.dto.responses.LikeStatusResponse

fun LikeStatusResponse.toDomain(): LikeStatus {
    return LikeStatus(
        liked = liked,
        likesCount = likesCount
    )
}