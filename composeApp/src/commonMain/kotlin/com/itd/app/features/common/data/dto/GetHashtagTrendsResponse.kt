package com.itd.app.features.common.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetHashtagTrendsResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val hashtags: List<HashtagPreviewDto>
    )
}

@Serializable
data class HashtagPreviewDto(
    val id: String,
    val name: String,
    val postsCount: Int
)