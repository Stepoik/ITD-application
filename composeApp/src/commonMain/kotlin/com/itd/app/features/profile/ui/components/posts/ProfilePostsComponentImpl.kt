package com.itd.app.features.profile.ui.components.posts

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.ui.list.mappers.toVO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class ProfilePostsComponentImpl(
    componentContext: ComponentContext,
    private val postsRepository: PostsRepository,
    private val username: String,
    private val onUserClicked: (String) -> Unit,
    private val onOpenPost: (String) -> Unit,
    private val onRepost: (String) -> Unit,
) : ProfilePostsComponent,
    BaseComponent<ProfilePostsState>(componentContext, ProfilePostsState.serializer()) {
    init {
        onLoadNext()
    }

    override fun initialState() = ProfilePostsState()

    override fun onLoadNext() {
        val state = state.value
        if (state.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch {
            postsRepository.getPostsByUser(
                username = username,
                offset = state.posts.lastOrNull()?.createdAt
            )
                .onSuccess { newPosts ->
                    updateState { it.copy(posts = it.posts + newPosts.map { it.toVO() }) }
                }
        }
    }

    override fun onOpenPost(postId: String) {
        onOpenPost.invoke(postId)
    }

    override fun onOpenUser(username: String) {
        onUserClicked.invoke(username.drop(1))
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

    class Factory : ProfilePostsComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            username: String,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit
        ): ProfilePostsComponent {
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