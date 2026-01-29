package com.itd.app.features.feed.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.itd.app.core.decompose.Component
import com.itd.app.core.decompose.EmptyState
import com.itd.app.features.feed.ui.list.PostsListComponent

interface FeedComponent : Component<EmptyState> {
    val stack: Value<ChildStack<*, Pages>>

    fun onChangeTab(tabIndex: Int)

    sealed class Pages {
        data class Popular(val component: PostsListComponent) : Pages()
        data class Following(val component: PostsListComponent) : Pages()
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onUserClicked: (String) -> Unit,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit,
        ): FeedComponent
    }
}