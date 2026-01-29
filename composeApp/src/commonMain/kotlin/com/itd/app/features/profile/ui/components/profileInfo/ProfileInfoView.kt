package com.itd.app.features.profile.ui.components.profileInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.core.datetime.format
import com.itd.app.core.utils.px
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.DefaultHorizontalDivider
import com.itd.app.uikit.components.HorizontalSpacer
import com.itd.app.uikit.components.VerticalSpacer
import com.itd.app.uikit.icons.Calendar
import com.itd.app.uikit.icons.Icons
import kotlinx.datetime.LocalDateTime

@Composable
fun ProfileInfoView(component: ProfileInfoComponent, modifier: Modifier = Modifier) {
    val state = component.state.subscribeAsState().value
    if (state.info != null) {
        val profileInfo = state.info
        Column(modifier) {
            Box {
                AsyncImage(
                    profileInfo.banner,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    Modifier.padding(start = 16.dp).offset(y = 60.dp)
                        .background(ITDTheme.colors.background, shape = CircleShape)
                        .size(120.dp)
                        .align(Alignment.BottomStart),
                    contentAlignment = Alignment.Center
                ) {
                    Text(profileInfo.avatar, fontSize = 60.px)
                }
            }
            VerticalSpacer(60.dp)
            ProfileBio(profileInfo)
            DefaultHorizontalDivider(1.dp, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ProfileBio(profileInfo: ProfileInfo) {
    Column(
        Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            profileInfo.displayName,
            style = ITDTheme.typography.titleLarge,
            color = ITDTheme.colors.onBackground
        )
        Text(
            profileInfo.username,
            style = ITDTheme.typography.body,
            color = ITDTheme.colors.secondary
        )
        if (profileInfo.bio != null) {
            Text(profileInfo.bio)
        }
        RegistrationInfo(profileInfo.createdAt)
        Row {
            FollowRow(profileInfo.followingCount, title = "Подписки")
            HorizontalSpacer(20.dp)
            FollowRow(profileInfo.followersCount, title = "Подписчики")
        }
    }
}

@Composable
private fun FollowRow(count: Int, title: String) {
    Row {
        Text(
            count.toString(),
            style = ITDTheme.typography.bodyUi,
            color = ITDTheme.colors.onBackground,
            fontWeight = FontWeight.Bold
        )
        HorizontalSpacer(4.dp)
        Text(title, style = ITDTheme.typography.bodyUi, color = ITDTheme.colors.secondary)
    }
}

@Composable
private fun RegistrationInfo(createdAt: LocalDateTime) {
    Row {
        Icon(
            Icons.Calendar,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = ITDTheme.colors.secondary
        )
        HorizontalSpacer(4.dp)
        Text(
            "Регистрация: ${createdAt.format("LLLL yyyy 'г.'")}",
            style = ITDTheme.typography.bodyUi,
            color = ITDTheme.colors.secondary
        )
    }
}