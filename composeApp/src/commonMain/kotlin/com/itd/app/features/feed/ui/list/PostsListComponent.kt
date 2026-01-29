package com.itd.app.features.feed.ui.list

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.features.feed.api.models.PostsType

interface PostsListComponent : Component<PostsState> {
    fun onGetPosts()

    fun onRefresh()

    fun onLoadNext()

    fun onLikePost(postId: String)

    fun onRepost(postId: String)

    fun onComment(postId: String)

    fun onPostClicked(postId: String)

    fun onUserClicked(username: String)

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            type: PostsType,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit
        ): PostsListComponent
    }
}