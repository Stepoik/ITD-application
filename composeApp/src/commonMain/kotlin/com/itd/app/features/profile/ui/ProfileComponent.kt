package com.itd.app.features.profile.ui

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.features.profile.ui.components.liked.ProfileLikedPostsComponent
import com.itd.app.features.profile.ui.components.posts.ProfilePostsComponent
import com.itd.app.features.profile.ui.components.profileInfo.ProfileInfoComponent

interface ProfileComponent : Component<ProfileState> {
    val profileInfo: ProfileInfoComponent
    val profilePosts: ProfilePostsComponent
    val likedPosts: ProfileLikedPostsComponent

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            username: String,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit
        ): ProfileComponent
    }
}