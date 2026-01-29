package com.itd.app.features.profile.ui.components.posts

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component

interface ProfilePostsComponent : Component<ProfilePostsState> {
    fun onLoadNext()

    fun onOpenPost(postId: String)

    fun onOpenUser(username: String)

    fun onLikeClicked(postId: String)

    fun onRepostClicked(postId: String)

    fun onCommentClicked(postId: String)

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            username: String,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit,
        ): ProfilePostsComponent
    }
}