package com.itd.app.features.feed.data.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class LikeStatusResponse(
    val liked: Boolean,
    val likesCount: Int
)
