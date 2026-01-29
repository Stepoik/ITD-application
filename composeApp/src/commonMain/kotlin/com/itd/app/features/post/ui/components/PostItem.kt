package com.itd.app.features.post.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itd.app.features.common.ui.toAgoText
import com.itd.app.features.feed.ui.list.mappers.formatThousand
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.HorizontalSpacer
import com.itd.app.uikit.icons.Comment
import com.itd.app.uikit.icons.Icons
import com.itd.app.uikit.icons.Like
import com.itd.app.uikit.icons.Menu
import com.itd.app.uikit.icons.Repost
import com.itd.app.uikit.icons.View
import kotlinx.datetime.LocalDateTime

private const val ACTION_BUTTON_DEFAULT_ALPHA = 0.4f

@Composable
fun PostItem(
    post: PostVO,
    onClick: () -> Unit,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onRepostClicked: () -> Unit,
    onUserClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.clickable(onClick = onClick).background(ITDTheme.colors.surface).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PostHeader(
            post.author,
            postDate = post.createdAt,
            onUserClicked = onUserClicked,
            modifier = Modifier.fillMaxWidth()
        )
        if (post.content.isNotEmpty()) {
            Text(post.content)
        }
        post.originalPost?.let {
            PostReferenceView(it)
        }
        if (post.attachments.isNotEmpty()) {
            MosaicAttachments(post.attachments, modifier = Modifier.fillMaxWidth())
        }
        PostActions(
            post,
            onCommentClicked = onCommentClicked,
            onLikeClicked = onLikeClicked,
            onRepostClicked = onRepostClicked,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PostHeader(
    author: AuthorVO,
    postDate: LocalDateTime,
    onUserClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val postText = postDate.toAgoText()
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(Modifier.clickable(onClick = onUserClicked)) {
            Box(Modifier.clip(CircleShape).size(40.dp), contentAlignment = Alignment.Center) {
                Text(author.avatar, fontSize = 24.sp)
            }
            HorizontalSpacer(12.dp)
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    author.displayName,
                    style = ITDTheme.typography.body,
                    fontWeight = FontWeight.SemiBold
                )
                Text(postText, style = ITDTheme.typography.caption)
            }
        }
        Icon(Icons.Menu, modifier = Modifier.size(18.dp), contentDescription = null)
    }
}

@Composable
fun PostActions(
    post: PostVO,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onRepostClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            LikeAction(
                post.likesCount.formatThousand(),
                onClick = onLikeClicked,
                isLiked = post.isLiked
            )
            CommentAction(post.commentsCount.toString(), onClick = onCommentClicked)
            RepostAction(post.repostsCount, isReposted = post.isReposted, onClick = onRepostClicked)
        }
        ViewsInfo(post.viewsCount)
    }
}

@Composable
private fun LikeAction(
    count: String,
    isLiked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (color, alpha) = if (isLiked) {
            ITDTheme.colors.like to 1.0f
        } else {
            ITDTheme.colors.onBackground to ACTION_BUTTON_DEFAULT_ALPHA
        }
        Icon(
            Icons.Like,
            modifier = Modifier.size(20.dp).alpha(alpha),
            contentDescription = null,
            tint = color
        )
        Text(count, color = color, modifier = Modifier.alpha(alpha))
    }
}

@Composable
private fun CommentAction(count: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Comment,
            modifier = Modifier.size(20.dp).alpha(ACTION_BUTTON_DEFAULT_ALPHA),
            contentDescription = null
        )
        Text(count, modifier = Modifier.alpha(ACTION_BUTTON_DEFAULT_ALPHA))
    }
}

@Composable
private fun RepostAction(
    count: String,
    isReposted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (color, alpha) = if (isReposted) {
            ITDTheme.colors.repost to 1.0f
        } else {
            ITDTheme.colors.onBackground to ACTION_BUTTON_DEFAULT_ALPHA
        }
        Icon(
            Icons.Repost,
            modifier = Modifier.size(20.dp).alpha(alpha),
            contentDescription = null,
            tint = color
        )
        Text(count, color = color, modifier = Modifier.alpha(alpha))
    }
}

@Composable
private fun ViewsInfo(count: String, modifier: Modifier = Modifier) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.View,
            modifier = Modifier.size(20.dp).alpha(ACTION_BUTTON_DEFAULT_ALPHA),
            contentDescription = null
        )
        Text(count, modifier = Modifier.alpha(ACTION_BUTTON_DEFAULT_ALPHA))
    }
}