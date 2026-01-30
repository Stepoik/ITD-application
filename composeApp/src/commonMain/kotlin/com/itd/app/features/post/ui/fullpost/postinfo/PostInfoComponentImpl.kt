package com.itd.app.features.post.ui.fullpost.postinfo

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.ui.list.mappers.toVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class PostInfoComponentImpl(
    componentContext: ComponentContext,
    private val postsRepository: PostsRepository,
    private val postId: String,
    private val onRepost: (String) -> Unit,
) : PostInfoComponent, BaseComponent<PostInfoState>(componentContext, PostInfoState.serializer()) {

    init {
        updateState { it.copy(isLoading = true) }
        componentScope.launch {
            postsRepository.getPostById(postId).onSuccess { post ->
                updateState { it.copy(post = post.toVO(), isLoading = false) }
            }
        }
    }

    override fun initialState() = PostInfoState()

    override fun onLike() {
        componentScope.launch(Dispatchers.Default) {
            val post = state.value.post ?: return@launch
            if (post.isLiked) {
                updateState {
                    it.copy(
                        post = post.copy(
                            likesCount = post.likesCount - 1,
                            isLiked = !post.isLiked
                        )
                    )
                }
            } else {
                updateState {
                    it.copy(
                        post = post.copy(
                            likesCount = post.likesCount + 1,
                            isLiked = !post.isLiked
                        )
                    )
                }
            }
            postsRepository.likePost(postId = postId, like = !post.isLiked).onFailure {
                updateState {
                    it.copy(post = post.copy(likesCount = post.likesCount, isLiked = post.isLiked))
                }
            }
        }
    }

    override fun onRepost() {
        onRepost.invoke(postId)
    }

    override fun onComment() {}

    class Factory : PostInfoComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            postId: String,
            onRepost: (String) -> Unit
        ): PostInfoComponent {
            return getKoin().get { parametersOf(componentContext, postId, onRepost) }
        }
    }
}