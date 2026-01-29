package com.itd.app.features.feed.ui.list

import com.itd.app.core.decompose.UIState
import com.itd.app.features.post.ui.components.PostVO
import kotlinx.serialization.Serializable

@Serializable
data class PostsState(
    val isLoading: Boolean,
    val error: PostsError?,
    val posts: List<PostVO>
) : UIState

enum class PostsError {
    LOADING
}