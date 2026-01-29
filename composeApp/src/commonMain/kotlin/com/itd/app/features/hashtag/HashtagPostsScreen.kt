package com.itd.app.features.hashtag

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.composables.core.Icon
import com.composeunstyled.Text
import com.itd.app.features.post.ui.components.PostItem
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.BaseScaffold
import com.itd.app.uikit.components.VerticalSpacer
import com.itd.app.uikit.icons.Back
import com.itd.app.uikit.icons.Icons

private const val POSTS_PAGINATION_THRESHOLD = 5

@Composable
fun HashtagPostsScreen(component: HashtagPostsComponent) {
    val state = component.state.subscribeAsState().value

    val lazyState = rememberLazyListState()
    LaunchedEffect(Unit) {
        snapshotFlow { lazyState.firstVisibleItemIndex + lazyState.layoutInfo.visibleItemsInfo.size }.collect {
            if (it > state.posts.size - POSTS_PAGINATION_THRESHOLD) {
                component.onLoadNext()
            }
        }
    }
    BaseScaffold {
        LazyColumn(state = lazyState, modifier = Modifier.padding(it)) {
            item {
                Column {
                    HashtagHeader(state.hashtagInfo, onBack = component::onNavigateBackClicked)
                    HorizontalDivider(
                        Modifier.fillMaxWidth().height(1.dp),
                        color = ITDTheme.colors.divider
                    )
                }
            }
            items(state.posts, key = { it.id }) {
                Column {
                    PostItem(
                        it,
                        onClick = { component.onOpenPost(it.id) },
                        onUserClicked = { component.onOpenUser(it.author.username) },
                        onLikeClicked = { component.onLike(it.id) },
                        onRepostClicked = { component.onRepost(it.id) },
                        onCommentClicked = { component.onComment(it.id) }
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

@Composable
private fun HashtagHeader(hashtagInfo: HashtagInfo, onBack: () -> Unit) {
    Row(
        Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Back, contentDescription = null, modifier = Modifier.clickable(onClick = onBack))
        Column {
            Text(
                hashtagInfo.hashtag,
                style = ITDTheme.typography.titleLarge,
                color = ITDTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            VerticalSpacer(4.dp)
            Text(
                hashtagInfo.postsCount ?: "",
                style = ITDTheme.typography.bodyUi,
                color = ITDTheme.colors.onBackgroundInactive
            )
        }
    }
}