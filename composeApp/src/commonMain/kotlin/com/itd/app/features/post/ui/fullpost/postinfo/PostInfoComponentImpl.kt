package com.itd.app.features.post.ui.fullpost.postinfo

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.ui.list.mappers.toVO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class PostInfoComponentImpl(
    componentContext: ComponentContext,
    private val postsRepository: PostsRepository,
    private val postId: String,
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

    class Factory : PostInfoComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext, postId: String): PostInfoComponent {
            return getKoin().get { parametersOf(componentContext, postId) }
        }
    }
}