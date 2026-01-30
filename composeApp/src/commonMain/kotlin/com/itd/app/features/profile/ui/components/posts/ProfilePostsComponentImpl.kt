package com.itd.app.features.profile.ui.components.posts

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.ui.list.mappers.toVO
import kotlinx.coroutines.Dispatchers
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
        componentScope.launch(Dispatchers.Default) {
            val posts = state.value.posts
            val post = posts.find { it.id == postId } ?: return@launch
            if (post.isLiked) {
                updatePost(postId = postId, likesCount = post.likesCount - 1, liked = !post.isLiked)
            } else {
                updatePost(postId = postId, likesCount = post.likesCount + 1, liked = !post.isLiked)
            }
            postsRepository.likePost(postId = postId, like = !post.isLiked).onFailure {
                updatePost(postId = postId, likesCount = post.likesCount, liked = post.isLiked)
            }
        }
    }

    override fun onRepostClicked(postId: String) {
        onRepost.invoke(postId)
    }

    override fun onCommentClicked(postId: String) {
        onOpenPost.invoke(postId)
    }

    private fun updatePost(postId: String, likesCount: Int, liked: Boolean) {
        // TODO Вынести в какой-то общий компонент, пока впадлу
        updateState {
            val posts = it.posts.map {
                if (it.id == postId) {
                    it.copy(
                        likesCount = likesCount,
                        isLiked = liked
                    )
                } else it
            }
            it.copy(posts = posts)
        }
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