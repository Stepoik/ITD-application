package com.itd.app.features.profile.ui.components.liked

import com.itd.app.core.decompose.UIState
import com.itd.app.features.post.ui.components.PostVO
import kotlinx.serialization.Serializable

@Serializable
data class ProfileLikedPostsState(
    val posts: List<PostVO> = listOf(),
    val isLoading: Boolean = false
): UIState
