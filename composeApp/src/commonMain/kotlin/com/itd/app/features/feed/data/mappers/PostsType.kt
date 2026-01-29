package com.itd.app.features.feed.data.mappers

import com.itd.app.features.feed.api.models.PostsType

fun PostsType.toNetworkType(): String {
    return when (this) {
        PostsType.POPULAR -> "popular"
        PostsType.FOLLOWING -> "following"
    }
}