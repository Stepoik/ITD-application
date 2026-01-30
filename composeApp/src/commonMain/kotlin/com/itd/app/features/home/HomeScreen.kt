package com.itd.app.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.active
import com.itd.app.features.feed.ui.Feed
import com.itd.app.features.hashtag.HashtagPostsScreen
import com.itd.app.features.notifications.ui.NotificationsScreen
import com.itd.app.features.post.ui.fullpost.FullPostScreen
import com.itd.app.features.post.ui.new.NewPostScreen
import com.itd.app.features.profile.ui.ProfileScreen
import com.itd.app.features.search.ui.SearchScreen
import com.itd.app.uikit.components.ITDFloatingActionButton
import com.itd.app.uikit.components.NavBottomSheet
import com.itd.app.uikit.components.RoundedTab
import com.itd.app.uikit.components.TabItem
import com.itd.app.uikit.components.rememberTabState
import com.itd.app.uikit.icons.Feed
import com.itd.app.uikit.icons.Icons
import com.itd.app.uikit.icons.Notification
import com.itd.app.uikit.icons.Plus
import com.itd.app.uikit.icons.Search
import com.itd.app.uikit.icons.View

@Composable
fun HomeScreen(component: HomeComponent) {
    val tabState = rememberTabState()
    LaunchedEffect(Unit) {
        component.stack.subscribe {
            val index = (it.backStack + it.active).map {
                when (it.instance) {
                    is HomeComponent.ChildTabs.Feed -> 0
                    is HomeComponent.ChildTabs.Search -> 1
                    is HomeComponent.ChildTabs.Notifications -> 2
                    is HomeComponent.ChildTabs.MeProfile -> 3
                    else -> 100
                }
            }.lastOrNull { it < 4 } ?: 0
            tabState.selected = index
        }
    }
    Box(Modifier.fillMaxSize()) {
        Children(component.stack) {
            when (val instance = it.instance) {
                is HomeComponent.ChildTabs.Feed -> {
                    Feed(instance.component)
                }

                is HomeComponent.ChildTabs.Search -> {
                    SearchScreen(instance.component)
                }

                is HomeComponent.ChildTabs.Profile -> {
                    ProfileScreen(instance.component)
                }

                is HomeComponent.ChildTabs.MeProfile -> {
                    ProfileScreen(instance.component)
                }

                is HomeComponent.ChildTabs.Notifications -> {
                    NotificationsScreen(instance.component)
                }

                is HomeComponent.ChildTabs.Hashtag -> {
                    HashtagPostsScreen(instance.component)
                }

                is HomeComponent.ChildTabs.NewPost -> {
                    NewPostScreen(instance.component)
                }
            }
        }
        Column(Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
            if (component.stack.subscribeAsState().value.active.instance is HomeComponent.ChildTabs.Feed) {
                ITDFloatingActionButton(
                    icon = Icons.Plus,
                    onClick = component::onNewPostClicked,
                    modifier = Modifier.align(Alignment.End).padding(end = 16.dp)
                )
            }
            if (component.stack.subscribeAsState().value.active.instance !is HomeComponent.ChildTabs.NewPost) {
                RoundedTab(
                    items = listOf(
                        TabItem.IconTabItem("Лента", icon = Icons.Feed),
                        TabItem.IconTabItem("Поиск", icon = Icons.Search),
                        TabItem.IconTabItem("Уведомления", icon = Icons.Notification),
                        TabItem.EmojiTabItem(
                            "Профиль",
                            icon = component.state.subscribeAsState().value.profileAvatar
                        )
                    ),
                    state = tabState,
                    onClickItem = component::onSelectTab,
                    modifier = Modifier.fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                )
            }
        }
    }

    NavBottomSheet(
        onHide = component::onHidePost,
        childSlot = component.postSlot.subscribeAsState().value
    ) {
        when (val instance = it?.instance) {
            is HomeComponent.ChildSlots.Post -> {
                FullPostScreen(instance.component)
            }

            else -> {}
        }
    }
}