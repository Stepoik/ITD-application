package com.itd.app.features.post.ui.fullpost

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.asFlow
import com.itd.app.features.post.ui.fullpost.comments.CommentsComponent
import com.itd.app.features.post.ui.fullpost.postinfo.PostInfoComponent
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class FullPostComponentImpl(
    componentContext: ComponentContext,
    private val postInfoComponentFactory: PostInfoComponent.Factory,
    private val commentsComponentFactory: CommentsComponent.Factory,
    private val postId: String
) : FullPostComponent, BaseComponent<FullPostState>(componentContext, FullPostState.serializer()) {
    override val postInfoComponent: PostInfoComponent =
        postInfoComponentFactory.create(childContext("post_info"), postId)

    override val commentComponent: CommentsComponent =
        commentsComponentFactory.create(childContext("comments"), postId)

    init {
        componentScope.launch {
            combine(
                postInfoComponent.state.asFlow(),
                commentComponent.state.asFlow()
            ) { postState, commentsState ->
                postState.isLoading && commentsState.isLoading
            }.collect { isLoading ->
                updateState { it.copy(isLoading = isLoading) }
            }
        }
    }

    override fun initialState() = FullPostState()

    class Factory : FullPostComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext, postId: String): FullPostComponent {
            return getKoin().get { parametersOf(componentContext, postId) }
        }
    }
}