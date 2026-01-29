package com.itd.app.features.profile.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.asFlow
import com.itd.app.features.profile.ui.components.liked.ProfileLikedPostsComponent
import com.itd.app.features.profile.ui.components.posts.ProfilePostsComponent
import com.itd.app.features.profile.ui.components.profileInfo.ProfileInfoComponent
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class ProfileComponentImpl(
    componentContext: ComponentContext,
    private val profileUsername: String,
    private val onUserClicked: (String) -> Unit,
    private val onOpenPost: (String) -> Unit,
    private val onRepost: (String) -> Unit,
    private val profileInfoComponentFactory: ProfileInfoComponent.Factory,
    private val profilePostsComponentFactory: ProfilePostsComponent.Factory,
    private val profileLikedPostsComponentFactory: ProfileLikedPostsComponent.Factory
) : ProfileComponent, BaseComponent<ProfileState>(componentContext, ProfileState.serializer()) {

    override val profileInfo: ProfileInfoComponent =
        profileInfoComponentFactory.create(
            componentContext.childContext("profile_info"),
            profileUsername
        )
    override val profilePosts: ProfilePostsComponent =
        profilePostsComponentFactory.create(
            componentContext.childContext("posts"),
            profileUsername,
            onUserClicked = onUserClicked,
            onOpenPost = onOpenPost,
            onRepost = onRepost
        )
    override val likedPosts: ProfileLikedPostsComponent =
        profileLikedPostsComponentFactory
            .create(
                componentContext.childContext("liked_posts"),
                profileUsername,
                onUserClicked = onUserClicked,
                onOpenPost = onOpenPost,
                onRepost = onRepost
            )

    init {
        componentScope.launch {
            profileInfo.state.asFlow().collect { profileInfoState ->
                updateState { it.copy(isLoading = profileInfoState.isLoading) }
            }
        }
    }

    override fun initialState() = ProfileState()

    class Factory : ProfileComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            username: String,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit
        ): ProfileComponent {
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