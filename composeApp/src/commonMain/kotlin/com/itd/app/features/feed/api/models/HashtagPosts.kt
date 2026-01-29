package com.itd.app.features.feed.api.models

data class HashtagPosts(
    val posts: List<Post>,
    val postsCount: Int
)
