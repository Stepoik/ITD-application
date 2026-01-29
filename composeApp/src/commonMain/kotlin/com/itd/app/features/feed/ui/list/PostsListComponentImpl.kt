package com.itd.app.features.feed.ui.list

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.api.models.PostsType
import com.itd.app.features.feed.ui.list.mappers.formatThousand
import com.itd.app.features.feed.ui.list.mappers.toVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class PostsListComponentImpl(
    componentContext: ComponentContext,
    private val postsRepository: PostsRepository,
    private val type: PostsType,
    private val onUserClicked: (String) -> Unit,
    private val onOpenPost: (String) -> Unit,
    private val onRepost: (String) -> Unit
) : PostsListComponent, BaseComponent<PostsState>(componentContext, PostsState.serializer()) {
    init {
        onGetPosts()
    }

    override fun initialState(): PostsState {
        return PostsState(isLoading = false, error = null, posts = listOf())
    }

    override fun onGetPosts() {
        refreshPosts()
    }

    override fun onRefresh() {
        refreshPosts()
    }

    override fun onLoadNext() {
        val state = state.value
        if (state.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch {
            postsRepository.getPosts(state.posts.size, postsType = type)
                .onSuccess { newPosts ->
                    updateState {
                        val updatedPosts = it.posts + newPosts.map { it.toVO() }
                        it.copy(isLoading = false, posts = updatedPosts)
                    }
                }
                .onFailure {
                    updateState { it.copy(isLoading = false, error = PostsError.LOADING) }
                }
        }
    }

    override fun onLikePost(postId: String) {
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

    override fun onRepost(postId: String) {
        onRepost.invoke(postId)
    }

    override fun onComment(postId: String) {
        onOpenPost(postId)
    }

    override fun onPostClicked(postId: String) {
        onOpenPost(postId)
    }

    override fun onUserClicked(username: String) {
        onUserClicked.invoke(username.drop(1))
    }

    private fun updatePost(postId: String, likesCount: Int, liked: Boolean) {
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

    private fun refreshPosts() {
        if (state.value.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch {
            postsRepository.getPosts(0, postsType = type)
                .onSuccess { newPosts ->
                    updateState { it.copy(isLoading = false, posts = newPosts.map { it.toVO() }) }
                }
                .onFailure {
                    updateState { it.copy(isLoading = false, error = PostsError.LOADING) }
                }
        }
    }

    class Factory : PostsListComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            type: PostsType,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit
        ): PostsListComponent {
            return getKoin().get {
                parametersOf(
                    componentContext,
                    type,
                    onUserClicked,
                    onOpenPost,
                    onRepost
                )
            }
        }
    }
}