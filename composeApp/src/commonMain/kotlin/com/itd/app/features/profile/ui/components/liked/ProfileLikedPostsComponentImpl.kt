package com.itd.app.features.profile.ui.components.liked

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.ui.list.mappers.toVO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class ProfileLikedPostsComponentImpl(
    componentContext: ComponentContext,
    private val postsRepository: PostsRepository,
    private val username: String,
    private val onUserClicked: (String) -> Unit,
    private val onOpenPost: (String) -> Unit,
    private val onRepost: (String) -> Unit,
) : ProfileLikedPostsComponent,
    BaseComponent<ProfileLikedPostsState>(componentContext, ProfileLikedPostsState.serializer()) {
    init {
        onLoadNext()
    }

    override fun initialState() = ProfileLikedPostsState()

    override fun onLoadNext() {
        val state = state.value
        if (state.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch {
            postsRepository.getPostsByUserLiked(
                username = username,
                offset = state.posts.lastOrNull()?.createdAt
            ).onSuccess { newPosts ->
                updateState { it.copy(posts = it.posts + newPosts.map { it.toVO() }) }
            }
        }
    }

    override fun onOpenPost(postId: String) {
        onOpenPost.invoke(postId)
    }

    override fun onOpenUser(username: String) {
        onUserClicked.invoke(username)
    }

    override fun onLikeClicked(postId: String) {
        TODO("Not yet implemented")
    }

    override fun onRepostClicked(postId: String) {
        onRepost.invoke(postId)
    }

    override fun onCommentClicked(postId: String) {
        onOpenPost.invoke(postId)
    }

    class Factory : ProfileLikedPostsComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            username: String,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit,
        ): ProfileLikedPostsComponent {
            return getKoin().get {
                parametersOf(
                    componentContext,
                    username,
                    onUserClicked,
                    onOpenPost,
                    onRepost
                )
            }
        }
    }
}