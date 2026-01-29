package com.itd.app.features.hashtag

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component

interface HashtagPostsComponent : Component<HashtagPostsState> {
    fun onOpenPost(postId: String)

    fun onLike(postId: String)

    fun onRepost(postId: String)

    fun onComment(postId: String)

    fun onOpenUser(username: String)

    fun onOpenHashtag(hashtag: String)

    fun onNavigateBackClicked()

    fun onLoadNext()

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            hashtag: String,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit,
            onOpenUser: (String) -> Unit,
            onOpenHashtag: (String) -> Unit,
            onBack: () -> Unit
        ): HashtagPostsComponent
    }
}