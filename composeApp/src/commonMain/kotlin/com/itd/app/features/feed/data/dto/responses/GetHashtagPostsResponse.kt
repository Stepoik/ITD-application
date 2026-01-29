package com.itd.app.features.feed.data.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class GetHashtagPostsResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val posts: List<PostDto>,
        val hashtag: HashtagInfo
    )

    @Serializable
    data class HashtagInfo(
        val postsCount: Int
    )
}
