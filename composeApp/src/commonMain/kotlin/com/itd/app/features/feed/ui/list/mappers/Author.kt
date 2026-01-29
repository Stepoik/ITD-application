package com.itd.app.features.feed.ui.list.mappers

import com.itd.app.features.feed.api.models.Author
import com.itd.app.features.post.ui.components.AuthorVO

fun Author.toVO(): AuthorVO {
    return AuthorVO(
        avatar = avatar,
        username = "@$username",
        displayName = displayName,
        isVerified = verified,
        id = id
    )
}