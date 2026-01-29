package com.itd.app.features.post.ui.fullpost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.features.post.ui.fullpost.comments.NewComment
import com.itd.app.features.post.ui.fullpost.comments.comments
import com.itd.app.features.post.ui.fullpost.postinfo.postInfoView
import com.itd.app.uikit.components.BaseScaffold
import com.itd.app.uikit.components.DefaultHorizontalDivider
import com.itd.app.uikit.components.verticalSpacer

private const val POSTS_PAGINATION_THRESHOLD = 5

@Composable
fun FullPostScreen(component: FullPostComponent) {
    val commentsState = component.commentComponent.state.subscribeAsState().value
    val lazyState = rememberLazyListState()
    LaunchedEffect(Unit) {
        snapshotFlow { lazyState.firstVisibleItemIndex + lazyState.layoutInfo.visibleItemsInfo.size }.collect {
            if (it > commentsState.comments.size - POSTS_PAGINATION_THRESHOLD) {
                component.commentComponent.onLoadNext()
            }
        }
    }

    BaseScaffold {
        val state = component.state.subscribeAsState()
        val isLoading = remember { derivedStateOf { state.value.isLoading } }.value
        Column(Modifier.padding(bottom = it.calculateBottomPadding())) {
            LazyColumn(
                state = lazyState,
                modifier = Modifier.weight(1f)
            ) {
                postInfoView(component.postInfoComponent)
                if (!isLoading) {
                    comments(component.commentComponent, commentsState)
                }
            }
            Column {
                DefaultHorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                NewComment(
                    commentsState.newCommentText,
                    onChange = component.commentComponent::onNewCommentChanged,
                    onSend = component.commentComponent::onSendCommentClicked,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}