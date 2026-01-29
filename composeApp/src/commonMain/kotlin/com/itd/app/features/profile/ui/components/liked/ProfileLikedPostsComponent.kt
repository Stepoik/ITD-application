package com.itd.app.features.profile.ui.components.liked

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component

interface ProfileLikedPostsComponent : Component<ProfileLikedPostsState> {
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
        ): ProfileLikedPostsComponent
    }
}