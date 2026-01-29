package com.itd.app.features.common.data.mappers

import com.itd.app.features.common.api.models.HashtagPreview
import com.itd.app.features.common.data.dto.HashtagPreviewDto

fun HashtagPreviewDto.toDomain(): HashtagPreview {
    return HashtagPreview(
        id = id,
        name = name,
        postsCount = postsCount
    )
}