package com.itd.app.features.hashtag

import com.itd.app.core.decompose.UIState
import com.itd.app.features.post.ui.components.PostVO
import kotlinx.serialization.Serializable

@Serializable
data class HashtagPostsState(
    val isLoading: Boolean = false,
    val hashtagInfo: HashtagInfo = HashtagInfo(),
    val posts: List<PostVO> = listOf()
): UIState

@Serializable
data class HashtagInfo(
    val hashtag: String = "",
    val postsCount: String? = null,
)
