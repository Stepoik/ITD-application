package com.itd.app.features.profile.ui.components.liked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itd.app.features.post.ui.components.PostItem
import com.itd.app.uikit.ITDTheme

fun LazyListScope.likedPosts(state: ProfileLikedPostsState, component: ProfileLikedPostsComponent) {
    items(state.posts, key = { it.id }) {
        Column {
            PostItem(
                it,
                { component.onOpenPost(it.id) },
                { component.onLikeClicked(it.id) },
                { component.onCommentClicked(it.id) },
                { component.onRepostClicked(it.id) },
                { component.onOpenUser(it.id) }
            )
            HorizontalDivider(Modifier.fillMaxWidth().height(1.dp), color = ITDTheme.colors.divider)
        }
    }
}