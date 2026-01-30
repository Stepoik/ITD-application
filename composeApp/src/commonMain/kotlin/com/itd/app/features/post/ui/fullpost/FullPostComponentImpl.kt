package com.itd.app.features.post.ui.fullpost

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.asFlow
import com.itd.app.features.post.ui.fullpost.comments.CommentsComponent
import com.itd.app.features.post.ui.fullpost.postinfo.PostInfoComponent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class FullPostComponentImpl(
    componentContext: ComponentContext,
    private val postInfoComponentFactory: PostInfoComponent.Factory,
    private val commentsComponentFactory: CommentsComponent.Factory,
    private val postId: String,
    private val onOpenUser: (String) -> Unit,
    private val onRepost: (String) -> Unit,
) : FullPostComponent, BaseComponent<FullPostState>(componentContext, FullPostState.serializer()) {
    override val postInfoComponent: PostInfoComponent =
        postInfoComponentFactory.create(
            childContext("post_info"),
            postId = postId,
            onRepost = onRepost
        )

    override val commentComponent: CommentsComponent =
        commentsComponentFactory.create(
            childContext("comments"),
            postId = postId,
            onOpenUser = onOpenUser
        )

    init {
        componentScope.launch {
            postInfoComponent.state.asFlow().collect { postInfo ->
                updateState { it.copy(isLoading = postInfo.isLoading) }
            }
        }
        componentScope.launch {
            state.asFlow().takeWhile { it.isLoading }.collect()
            commentComponent.onLoadNext()
        }
    }

    override fun initialState() = FullPostState()

    class Factory : FullPostComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            postId: String,
            onOpenUser: (String) -> Unit,
            onRepost: (String) -> Unit
        ): FullPostComponent {
            return getKoin().get { parametersOf(componentContext, postId, onOpenUser, onRepost) }
        }
    }
}