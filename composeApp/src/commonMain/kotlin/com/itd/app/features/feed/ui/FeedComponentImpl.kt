package com.itd.app.features.feed.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.EmptyState
import com.itd.app.features.feed.api.models.PostsType
import com.itd.app.features.feed.ui.list.PostsListComponent
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class FeedComponentImpl(
    componentContext: ComponentContext,
    private val postsFactory: PostsListComponent.Factory,
    private val onUserClicked: (String) -> Unit,
    private val onOpenPost: (String) -> Unit,
    private val onRepost: (String) -> Unit
) : FeedComponent, BaseComponent<EmptyState>(componentContext, EmptyState.serializer()) {
    override fun initialState() = EmptyState

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, FeedComponent.Pages>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Popular,
        childFactory = ::createPagesChild
    )

    override fun onChangeTab(tabIndex: Int) {
        val config = when (tabIndex) {
            0 -> Config.Popular
            else -> Config.Following
        }
        navigation.pushToFront(config)
    }

    private fun createPagesChild(
        config: Config,
        context: ComponentContext
    ): FeedComponent.Pages {
        return when (config) {
            is Config.Popular -> {
                FeedComponent.Pages.Popular(
                    postsFactory.create(
                        componentContext = context,
                        type = PostsType.POPULAR,
                        onUserClicked = onUserClicked,
                        onOpenPost = onOpenPost,
                        onRepost = onRepost
                    )
                )
            }

            is Config.Following -> {
                FeedComponent.Pages.Following(
                    postsFactory.create(
                        componentContext = context,
                        type = PostsType.FOLLOWING,
                        onUserClicked = onUserClicked,
                        onOpenPost = onOpenPost,
                        onRepost = onRepost
                    )
                )
            }
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Popular : Config()

        @Serializable
        data object Following : Config()

    }

    class Factory : FeedComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit,
        ): FeedComponent {
            return getKoin().get {
                parametersOf(
                    componentContext,
                    onUserClicked,
                    onOpenPost,
                    onRepost
                )
            }
        }
    }
}