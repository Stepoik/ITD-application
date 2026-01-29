package com.itd.app.features.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itd.app.core.utils.SerializableTextFieldValue
import com.itd.app.core.utils.px
import com.itd.app.core.utils.serializable
import com.itd.app.core.utils.text
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.BaseScaffold
import com.itd.app.uikit.components.HorizontalSpacer
import com.itd.app.uikit.components.verticalSpacer
import com.itd.app.uikit.icons.Icons
import com.itd.app.uikit.icons.Search
import com.itd.app.uikit.icons.Verification

@Composable
fun SearchScreen(component: SearchComponent) {
    val state = component.state.subscribeAsState()
    val searchListState = remember { derivedStateOf { state.value.toListState() } }.value
    BaseScaffold {
        LazyColumn(Modifier.padding(it)) {
            item {
                val searchTextState = remember { derivedStateOf { state.value.searchText } }.value
                SearchTextField(
                    searchTextState,
                    onChange = component::onTextChanged,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                )
            }
            searchList(component, searchListState)
        }
    }
}

@Composable
private fun SearchTextField(
    textFieldValue: SerializableTextFieldValue,
    onChange: (SerializableTextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        textFieldValue.textFieldValue,
        onValueChange = { onChange(it.serializable()) },
        modifier = modifier
    ) { innerTextField ->
        Row(
            Modifier.background(ITDTheme.colors.inputBg, shape = CircleShape).fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Search,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = ITDTheme.colors.secondary
            )
            HorizontalSpacer(16.dp)
            Box {
                if (textFieldValue.text.isEmpty()) {
                    Text(
                        "Поиск пользователей и хештегов",
                        style = ITDTheme.typography.bodyUi,
                        color = ITDTheme.colors.onBackground
                    )
                }
                innerTextField()
            }
        }
    }
}

private fun LazyListScope.searchList(component: SearchComponent, state: SearchListState) {
    when (state) {
        is SearchListState.Loading -> {
            item {
                Loading()
            }
        }

        is SearchListState.SearchFound -> {
            foundUsers(component, state.foundUsers)
            foundHashTags(component, state.foundHashtags)
            verticalSpacer(100.dp)
        }

        is SearchListState.EmptySearch -> {
            popularHashTags(component, state.popularHashtags)
            popularUsers(component, popularUsers = state.popularUsers)
            topClans(state.topClans)
            verticalSpacer(100.dp)
        }
    }
}

private fun LazyListScope.foundHashTags(
    component: SearchComponent,
    foundHashtags: List<HashtagVO>
) {
    item {
        ChapterTitle("Хэштеги")
    }
    items(foundHashtags, key = { it.id }) {
        HashtagItem(it, onClick = { component.onOpenHashtag(it.name) })
    }
}

private fun LazyListScope.foundUsers(
    component: SearchComponent,
    foundUsers: List<UserVO>
) {
    item {
        ChapterTitle("Пользователи")
    }
    items(foundUsers, key = { it.id }) {
        UsersItem(it, onClick = { component.onOpenUser(it.username) })
    }
}

private fun LazyListScope.popularHashTags(
    component: SearchComponent,
    popularHashtags: List<HashtagVO>
) {
    item {
        ChapterTitle("Популярные хэштеги")
    }
    items(popularHashtags, key = { it.id }) {
        HashtagItem(it, onClick = { component.onOpenHashtag(it.name) })
    }
}

private fun LazyListScope.popularUsers(
    component: SearchComponent,
    popularUsers: List<UserVO>
) {
    item {
        ChapterTitle("Кого читать")
    }
    items(popularUsers, key = { it.id }) {
        UsersItem(it, onClick = { component.onOpenUser(it.username) })
    }
}

private fun LazyListScope.topClans(
    clans: List<ClanVO>
) {
    item {
        ChapterTitle("Топ кланов")
    }
    item {
        TopClans(clans, modifier = Modifier.padding(top = 8.dp).padding(horizontal = 16.dp))
    }
}


@Composable
private fun ChapterTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = ITDTheme.typography.titleMedium,
        color = ITDTheme.colors.onBackground,
        modifier = modifier.padding(vertical = 12.dp, horizontal = 16.dp)
    )
}

@Composable
private fun HashtagItem(hashtag: HashtagVO, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            hashtag.index,
            modifier = Modifier.size(24.dp),
            style = ITDTheme.typography.bodyUi,
            color = ITDTheme.colors.secondary
        )
        HorizontalSpacer(12.dp)
        Column {
            Text(
                hashtag.name,
                style = ITDTheme.typography.body,
                fontWeight = FontWeight.Bold,
                color = ITDTheme.colors.onBackground
            )
            Text(
                hashtag.postsCount,
                style = ITDTheme.typography.bodyUi,
                color = ITDTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun UsersItem(user: UserVO, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            user.avatar,
            modifier = Modifier.size(24.dp),
            fontSize = 20.px
        )
        HorizontalSpacer(12.dp)
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    user.displayName,
                    style = ITDTheme.typography.body,
                    color = ITDTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold
                )
                if (user.verified) {
                    Icon(
                        Icons.Verification,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Text(
                user.username,
                style = ITDTheme.typography.bodyUi,
                fontWeight = FontWeight.Bold,
                color = ITDTheme.colors.secondary
            )
            Text(
                user.followersCount,
                style = ITDTheme.typography.small,
                color = ITDTheme.colors.onBackgroundInactive
            )
        }
    }
}

@Composable
private fun TopClans(clans: List<ClanVO>, modifier: Modifier = Modifier) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        clans.forEach {
            ClanChip(it)
        }
    }
}

@Composable
private fun ClanChip(clan: ClanVO) {
    val (indexColor, backgroundColor) = if (clan.highlighted) {
        Color(0xFF1d9bf0) to Color(0x1a1d9bf0)
    } else {
        ITDTheme.colors.secondary to ITDTheme.colors.background
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(backgroundColor, shape = CircleShape)
            .padding(vertical = 6.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            clan.index,
            style = ITDTheme.typography.small,
            color = indexColor,
            modifier = Modifier.defaultMinSize(minWidth = 16.dp)
        )
        Text(clan.avatar, fontSize = 20.px)
        Text(
            clan.memberCount,
            style = ITDTheme.typography.small,
            color = ITDTheme.colors.secondary
        )
    }
}

@Composable
private fun Loading() {

}