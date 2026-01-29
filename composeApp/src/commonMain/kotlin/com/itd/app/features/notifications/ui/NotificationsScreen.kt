package com.itd.app.features.notifications.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.core.utils.px
import com.itd.app.features.common.ui.toAgoText
import com.itd.app.features.notifications.api.models.NotificationType
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.BaseScaffold
import com.itd.app.uikit.components.DefaultHorizontalDivider
import com.itd.app.uikit.components.TabRow
import com.itd.app.uikit.components.VerticalSpacer
import com.itd.app.uikit.components.horizontalDivider
import com.itd.app.uikit.icons.CommentNotification
import com.itd.app.uikit.icons.FollowNotification
import com.itd.app.uikit.icons.Icons
import com.itd.app.uikit.icons.LikeNotification
import com.itd.app.uikit.icons.ReplyNotification
import com.itd.app.uikit.icons.RepostNotification

@Composable
fun NotificationsScreen(component: NotificationComponent) {
    val state = component.state.subscribeAsState().value
    var selectedTab by remember { mutableStateOf(0) }
    BaseScaffold {
        LazyColumn(Modifier.padding(it)) {
            item {
                Title("Уведомления")
            }
            horizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())
            item {
                TabRow(
                    tabs = listOf("Все", "Упоминания"),
                    selected = selectedTab,
                    onChangeTab = { selectedTab = it }
                )
            }
            if (selectedTab == 0) {
                items(state.allNotifications, key = { it.id }) {
                    Column {
                        NotificationItem(it)
                        DefaultHorizontalDivider(
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                items(state.mentions, key = { it.id }) {
                    Column {
                        NotificationItem(it)
                        DefaultHorizontalDivider(
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Title(text: String) {
    Text(
        text,
        style = ITDTheme.typography.titleSmall,
        color = ITDTheme.colors.onBackground,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    )
}

@Composable
private fun NotificationItem(notification: NotificationVO) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth().clickable(onClick = {}).padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        AuthorTypeBox(notification.author, notification.type)
        Column {
            Row {
                Text(
                    notification.author.displayName,
                    style = ITDTheme.typography.bodyUi,
                    color = ITDTheme.colors.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    " ${notification.type.toText()}",
                    style = ITDTheme.typography.bodyUi,
                    color = ITDTheme.colors.secondary
                )
            }
            if (!notification.preview.isNullOrBlank()) {
                VerticalSpacer(4.dp)
                Text(
                    notification.preview,
                    style = ITDTheme.typography.bodyUi,
                    color = ITDTheme.colors.onBackgroundInactive
                )
            }
            VerticalSpacer(4.dp)
            Text(
                notification.createdAt.toAgoText(),
                style = ITDTheme.typography.small,
                color = ITDTheme.colors.onBackgroundInactive
            )
        }
    }
}

@Composable
private fun AuthorTypeBox(
    author: NotificationAuthorVO,
    type: NotificationType,
    modifier: Modifier = Modifier
) {
    Box(modifier.size(44.dp)) {
        Text(author.avatar, fontSize = 26.px, modifier = Modifier.align(Alignment.Center))
        NotificationBadge(type = type, modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
private fun NotificationBadge(type: NotificationType, modifier: Modifier = Modifier) {
    Box(
        modifier.size(24.dp).background(ITDTheme.colors.background, shape = CircleShape)
            .padding(2.dp).background(type.toBadgeColor(), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            type.toImageVector(),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier.size(10.dp)
        )
    }
}

private fun NotificationType.toImageVector(): ImageVector {
    return when (this) {
        NotificationType.COMMENT -> Icons.CommentNotification
        NotificationType.REPLY -> Icons.ReplyNotification
        NotificationType.LIKE -> Icons.LikeNotification
        NotificationType.REPOST -> Icons.RepostNotification
        NotificationType.FOLLOW -> Icons.FollowNotification
    }
}

private fun NotificationType.toText(): String {
    return when (this) {
        NotificationType.COMMENT -> "прокомментиовал ваш пост"
        NotificationType.REPLY -> "ответил на ваш комментарий"
        NotificationType.LIKE -> "оценил ваш пост"
        NotificationType.REPOST -> "сделал репост вашего поста"
        NotificationType.FOLLOW -> "подписался на вас"
    }
}

private fun NotificationType.toBadgeColor(): Color {
    return when (this) {
        NotificationType.COMMENT -> Color(0xFF1d9bf0)
        NotificationType.REPLY -> Color(0xFF8b5cf6)
        NotificationType.LIKE -> Color(0xFFff5050)
        NotificationType.REPOST -> Color(0xFF00ba7c)
        NotificationType.FOLLOW -> Color(0xFF1d9bf0)
    }
}