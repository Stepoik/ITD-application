package com.itd.app.features.post.ui.fullpost

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.features.post.ui.fullpost.comments.CommentsComponent
import com.itd.app.features.post.ui.fullpost.postinfo.PostInfoComponent

interface FullPostComponent : Component<FullPostState> {
    val postInfoComponent: PostInfoComponent

    val commentComponent: CommentsComponent

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            postId: String,
            onOpenUser: (String) -> Unit,
            onRepost: (String) -> Unit
        ): FullPostComponent
    }
}