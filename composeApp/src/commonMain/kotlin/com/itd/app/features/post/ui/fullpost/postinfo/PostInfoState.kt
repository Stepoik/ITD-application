package com.itd.app.features.post.ui.fullpost.postinfo

import com.itd.app.core.decompose.UIState
import com.itd.app.features.post.ui.components.PostVO
import kotlinx.serialization.Serializable

@Serializable
data class PostInfoState(
    val isLoading: Boolean = false,
    val post: PostVO? = null
): UIState
