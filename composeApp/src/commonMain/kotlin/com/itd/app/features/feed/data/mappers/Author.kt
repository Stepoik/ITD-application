package com.itd.app.features.feed.data.mappers

import com.itd.app.features.feed.api.models.Author
import com.itd.app.features.feed.data.dto.responses.AuthorDto

fun AuthorDto.toDomain(): Author {
    return Author(
        avatar = avatar,
        displayName = displayName,
        id = id,
        username = username ?: "",
        verified = verified
    )
}