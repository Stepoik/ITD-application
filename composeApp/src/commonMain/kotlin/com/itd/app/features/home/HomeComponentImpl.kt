package com.itd.app.features.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.ui.FeedComponent
import com.itd.app.features.hashtag.HashtagPostsComponent
import com.itd.app.features.notifications.ui.NotificationComponent
import com.itd.app.features.post.ui.fullpost.FullPostComponent
import com.itd.app.features.post.ui.new.NewPostComponent
import com.itd.app.features.profile.api.ProfileRepository
import com.itd.app.features.profile.ui.ProfileComponent
import com.itd.app.features.search.ui.SearchComponent
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class HomeComponentImpl(
    componentContext: ComponentContext,
    private val profileRepository: ProfileRepository,
    private val feedComponentFactory: FeedComponent.Factory,
    private val searchComponentFactory: SearchComponent.Factory,
    private val notificationComponentFactory: NotificationComponent.Factory,
    private val profileComponentFactory: ProfileComponent.Factory,
    private val fullPostComponentFactory: FullPostComponent.Factory,
    private val hashtagPostsComponentFactory: HashtagPostsComponent.Factory,
    private val newPostComponentFactory: NewPostComponent.Factory
) : HomeComponent, BaseComponent<HomeState>(componentContext, HomeState.serializer()) {
    init {
        componentScope.launch {
            for (i in 0 until 3) {
                profileRepository.getMe().onSuccess { user ->
                    updateState {
                        it.copy(
                            profileAvatar = user.avatar,
                            profileUsername = user.username
                        )
                    }
                    break
                }
            }
        }
    }

    override fun initialState() = HomeState("", "")

    private val navigation = StackNavigation<Config>()
    private val slotNavigation = SlotNavigation<PostSlot>()

    override val stack: Value<ChildStack<*, HomeComponent.ChildTabs>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Feed,
        childFactory = ::createPagesChild,
        handleBackButton = true
    )

    override val postSlot: Value<ChildSlot<*, HomeComponent.ChildSlots>> = childSlot(
        source = slotNavigation,
        serializer = PostSlot.serializer(),
        handleBackButton = true,
        childFactory = { config, context ->
            HomeComponent.ChildSlots.Post(
                fullPostComponentFactory.create(
                    context,
                    postId = config.postId,
                    onOpenUser = {
                        slotNavigation.dismiss()
                        navigation.pushNew(Config.Profile(it))
                    },
                    onRepost = {
                    }
                )
            )
        }
    )

    override fun onSelectTab(tabIndex: Int) {
        val config = when (tabIndex) {
            0 -> Config.Feed
            1 -> Config.Search
            2 -> Config.Notifications
            else -> Config.MeProfile
        }
        navigation.pushToFront(config)
    }

    override fun onHidePost() {
        slotNavigation.dismiss()
    }

    override fun onNewPostClicked() {
        navigation.pushNew(Config.NewPost())
    }

    private fun createPagesChild(
        config: Config,
        context: ComponentContext
    ): HomeComponent.ChildTabs {
        return when (config) {
            is Config.Feed -> {
                HomeComponent.ChildTabs.Feed(
                    feedComponentFactory.create(
                        context,
                        onUserClicked = {
                            navigation.pushNew(Config.Profile(it))
                        },
                        onOpenPost = {
                            slotNavigation.activate(PostSlot(it))
                        },
                        onRepost = {

                        }
                    )
                )
            }

            is Config.Profile -> {
                HomeComponent.ChildTabs.Profile(
                    createProfileComponent(
                        context,
                        config.username
                    )
                )
            }

            is Config.MeProfile -> {
                HomeComponent.ChildTabs.MeProfile(
                    createProfileComponent(
                        context,
                        state.value.profileUsername
                    )
                )
            }

            is Config.Search -> {
                HomeComponent.ChildTabs.Search(
                    searchComponentFactory.create(
                        context,
                        onOpenHashtag = { navigation.pushNew(Config.Hashtag(it)) },
                        onOpenUser = { navigation.pushNew(Config.Profile(it)) }
                    )
                )
            }

            is Config.Notifications -> {
                HomeComponent.ChildTabs.Notifications(notificationComponentFactory.create(context))
            }

            is Config.Hashtag -> {
                HomeComponent.ChildTabs.Hashtag(
                    hashtagPostsComponentFactory.create(
                        context,
                        hashtag = config.hashtag,
                        onOpenUser = {
                            navigation.pushNew(Config.Profile(it))
                        },
                        onRepost = {
                        },
                        onOpenPost = {
                            slotNavigation.activate(HomeComponentImpl.PostSlot(it))
                        },
                        onOpenHashtag = {
                            navigation.pushNew(Config.Hashtag(it))
                        },
                        onBack = {
                            navigation.pop()
                        }
                    )
                )
            }

            is Config.NewPost -> {
                HomeComponent.ChildTabs.NewPost(
                    newPostComponentFactory.create(
                        context,
                        onClose = {
                            navigation.pop()
                        }
                    )
                )
            }
        }
    }

    private fun createProfileComponent(
        context: ComponentContext,
        username: String
    ): ProfileComponent {
        return profileComponentFactory.create(
            context,
            username,
            onRepost = {},
            onOpenPost = {
                slotNavigation.activate(PostSlot(it))
            },
            onUserClicked = {
                navigation.pushNew(Config.Profile(it))
            }
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        object Feed : Config()

        @Serializable
        object Search : Config()

        @Serializable
        object Notifications : Config()

        @Serializable
        class Profile(val username: String) : Config()

        @Serializable
        data object MeProfile : Config()

        @Serializable
        class Hashtag(val hashtag: String) : Config()

        @Serializable
        class NewPost : Config()
    }

    @Serializable
    private data class PostSlot(val postId: String)

    class Factory : HomeComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext): HomeComponent {
            return getKoin().get { parametersOf(componentContext) }
        }
    }
}