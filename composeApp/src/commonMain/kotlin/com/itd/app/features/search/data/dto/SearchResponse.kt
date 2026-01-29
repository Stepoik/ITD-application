package com.itd.app.features.search.data.dto

import com.itd.app.features.common.data.dto.HashtagPreviewDto
import com.itd.app.features.common.data.dto.UserPreviewDto
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val hashtags: List<HashtagPreviewDto>,
        val users: List<UserPreviewDto>
    )
}
