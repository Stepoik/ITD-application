package com.itd.app.features.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.features.profile.ui.components.liked.likedPosts
import com.itd.app.features.profile.ui.components.posts.profilePosts
import com.itd.app.features.profile.ui.components.profileInfo.ProfileInfoView
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.BaseScaffold
import com.itd.app.uikit.components.DefaultHorizontalDivider
import com.itd.app.uikit.components.verticalSpacer

private const val POSTS_PAGINATION_THRESHOLD = 5

@Composable
fun ProfileScreen(component: ProfileComponent) {
    var selectedTab by remember { mutableStateOf(0) }
    val profilePostsState = component.profilePosts.state.subscribeAsState().value
    val profileLikedPostsState = component.likedPosts.state.subscribeAsState().value
    val lazyState = rememberLazyListState()
    LaunchedEffect(Unit) {
        snapshotFlow { lazyState.firstVisibleItemIndex + lazyState.layoutInfo.visibleItemsInfo.size }.collect {
            if (selectedTab == 0) {
                if (it > profilePostsState.posts.size - POSTS_PAGINATION_THRESHOLD) {
                    component.profilePosts.onLoadNext()
                }
            } else {
                if (it > profilePostsState.posts.size - POSTS_PAGINATION_THRESHOLD) {
                    component.likedPosts.onLoadNext()
                }
            }
        }
    }
    BaseScaffold {
        if (component.state.subscribeAsState().value.isLoading) {
            Box(Modifier.fillMaxSize())
        } else {
            LazyColumn(Modifier.padding(it), state = lazyState) {
                item {
                    ProfileInfoView(component.profileInfo)
                }
                item {
                    ProfileTabs(
                        tabs = listOf("Посты", "Понравившиеся"),
                        selected = selectedTab,
                        onSelect = { selectedTab = it }
                    )
                }

                if (selectedTab == 0) {
                    profilePosts(profilePostsState, component.profilePosts)
                } else {
                    likedPosts(profileLikedPostsState, component.likedPosts)
                }

                verticalSpacer(100.dp)
            }
        }
    }
}

@Composable
private fun ProfileTabs(tabs: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    Column {
        Row {
            tabs.forEachIndexed { index, title ->
                ProfileTab(
                    title,
                    selected = selected == index,
                    onClick = { onSelect(index) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        DefaultHorizontalDivider(1.dp, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun ProfileTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        val fontWeight = if (selected) {
            FontWeight.Bold
        } else {
            FontWeight.Normal
        }
        Text(
            title,
            modifier = Modifier.padding(16.dp),
            style = ITDTheme.typography.body,
            color = ITDTheme.colors.onBackground,
            fontWeight = fontWeight
        )
        if (selected) {
            Box(
                Modifier.size(width = 56.dp, 2.dp).background(ITDTheme.colors.primary)
                    .clip(CircleShape)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}