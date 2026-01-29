package com.itd.app.features.post.ui.fullpost.postinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.features.post.ui.components.MosaicAttachments
import com.itd.app.features.post.ui.components.PostActions
import com.itd.app.features.post.ui.components.PostHeader
import com.itd.app.features.post.ui.components.PostReferenceView
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.DefaultHorizontalDivider

fun LazyListScope.postInfoView(component: PostInfoComponent) {
    stickyHeader(key = "post_info_header") {
        val state = component.state.subscribeAsState().value
        val post = state.post
        if (post != null) {
            PostHeader(
                post.author,
                post.createdAt,
                onUserClicked = {},
                modifier = Modifier.fillMaxWidth().background(ITDTheme.colors.background)
                    .padding(vertical = 16.dp, horizontal = 20.dp)
            )
        }
    }
    item(key = "post_info") {
        val state = component.state.subscribeAsState().value
        val post = state.post
        if (post != null) {
            Column {
                Column(
                    Modifier.background(ITDTheme.colors.surface)
                        .padding(vertical = 12.dp, horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (post.content.isNotEmpty()) {
                        Text(post.content)
                    }
                    post.originalPost?.let {
                        PostReferenceView(it)
                    }
                    if (post.attachments.isNotEmpty()) {
                        MosaicAttachments(post.attachments, modifier = Modifier.fillMaxWidth())
                    }
                }
                DefaultHorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                PostActions(
                    post,
                    onCommentClicked = {},
                    onLikeClicked = { },
                    onRepostClicked = {},
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)
                )
                DefaultHorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}