package com.itd.app.features.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.itd.app.core.decompose.Component
import com.itd.app.features.feed.ui.FeedComponent
import com.itd.app.features.notifications.ui.NotificationComponent
import com.itd.app.features.post.ui.fullpost.FullPostComponent
import com.itd.app.features.profile.ui.ProfileComponent
import com.itd.app.features.search.ui.SearchComponent

interface HomeComponent : Component<HomeState> {
    val stack: Value<ChildStack<*, ChildTabs>>

    val postSlot: Value<ChildSlot<*, ChildSlots>>

    sealed class ChildTabs {
        data class Feed(val component: FeedComponent) : ChildTabs()
        data class Search(val component: SearchComponent) : ChildTabs()
        data class Notifications(val component: NotificationComponent) : ChildTabs()
        data class MeProfile(val component: ProfileComponent) : ChildTabs()
        data class Profile(val component: ProfileComponent) : ChildTabs()
    }

    sealed class ChildSlots {
        data class Post(val component: FullPostComponent) : ChildSlots()
    }

    fun onSelectTab(tabIndex: Int)

    fun onHidePost()

    interface Factory {
        fun create(componentContext: ComponentContext): HomeComponent
    }
}