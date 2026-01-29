package com.itd.app.features.feed.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.core.haze.HazeProvider
import com.itd.app.core.haze.LocalHazeState
import com.itd.app.features.feed.ui.list.PostsListScreen
import com.itd.app.uikit.components.BaseScaffold
import com.itd.app.uikit.components.TabRow
import dev.chrisbanes.haze.hazeEffect

@Composable
fun Feed(component: FeedComponent) {
    val header: @Composable () -> Unit = {
        TabRow(
            tabs = listOf("Популярное", "Подписки"),
            selected = component.stack.subscribeAsState().value.active.instance.toInt(),
            onChangeTab = component::onChangeTab,
            modifier = Modifier.fillMaxWidth().hazeEffect(LocalHazeState.current).statusBarsPadding()
        )
    }
    HazeProvider {
        BaseScaffold {
            Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
                Children(component.stack) {
                    when (val instance = it.instance) {
                        is FeedComponent.Pages.Popular -> {
                            PostsListScreen(instance.component, header)
                        }

                        is FeedComponent.Pages.Following -> {
                            PostsListScreen(instance.component, header)
                        }
                    }
                }
            }
        }
    }
}

private fun FeedComponent.Pages.toInt(): Int {
    return when (this) {
        is FeedComponent.Pages.Popular -> 0
        else -> 1
    }
}