package com.itd.app.features.feed.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.core.haze.LocalHazeState
import com.itd.app.features.post.ui.components.PostItem
import com.itd.app.uikit.ITDTheme
import dev.chrisbanes.haze.hazeSource

private const val POSTS_PAGINATION_THRESHOLD = 5

@Composable
fun PostsListScreen(component: PostsListComponent, stickyHeader: @Composable () -> Unit) {
    val state = component.state.subscribeAsState().value
    PostsListScreen(component, state, stickyHeader)
}

@Composable
private fun PostsListScreen(
    component: PostsListComponent,
    state: PostsState,
    stickyHeader: @Composable () -> Unit
) {
    val lazyState = rememberLazyListState()
    LaunchedEffect(Unit) {
        snapshotFlow { lazyState.firstVisibleItemIndex + lazyState.layoutInfo.visibleItemsInfo.size }.collect {
            if (it > state.posts.size - POSTS_PAGINATION_THRESHOLD) {
                component.onLoadNext()
            }
        }
    }
    LazyColumn(
        state = lazyState,
        modifier = Modifier.background(ITDTheme.colors.background).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        stickyHeader { stickyHeader() }
        if (state.isLoading && state.posts.isEmpty()) {
            item {  }
        } else {
            if (state.posts.isEmpty()) {
                item {
                    Text(
                        "Пока нет постов",
                        modifier = Modifier.padding(vertical = 48.dp, horizontal = 24.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = ITDTheme.typography.body,
                        color = ITDTheme.colors.secondary
                    )
                }
            } else {
                items(state.posts, key = { it.id }) {
                    Column {
                        PostItem(
                            it,
                            onClick = { component.onPostClicked(it.id) },
                            onLikeClicked = { component.onLikePost(it.id) },
                            onRepostClicked = { component.onRepost(it.id) },
                            onCommentClicked = { component.onComment(it.id) },
                            onUserClicked = { component.onUserClicked(it.author.username) },
                            modifier = Modifier.hazeSource(LocalHazeState.current)
                        )
                        HorizontalDivider(
                            Modifier.fillMaxWidth().height(1.dp),
                            color = ITDTheme.colors.divider
                        )
                    }
                }
            }
        }
    }
}