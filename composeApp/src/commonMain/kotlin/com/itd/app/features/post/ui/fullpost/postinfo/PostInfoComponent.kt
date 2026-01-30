package com.itd.app.features.post.ui.fullpost.postinfo

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component

interface PostInfoComponent : Component<PostInfoState> {
    fun onLike()

    fun onRepost()

    fun onComment()

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            postId: String,
            onRepost: (String) -> Unit
        ): PostInfoComponent
    }
}