package com.itd.app.uikit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.itd.app.uikit.ITDTheme

@Stable
class TabState {
    var selected by mutableStateOf(0)
}

@Composable
fun rememberTabState(): TabState {
    return remember {
        TabState()
    }
}

sealed class TabItem {
    data class IconTabItem(
        val title: String,
        val icon: ImageVector
    ): TabItem()

    data class EmojiTabItem(
        val title: String,
        val icon: String
    ): TabItem()
}

@Composable
fun RoundedTab(
    items: List<TabItem>,
    onClickItem: (Int) -> Unit,
    state: TabState = rememberTabState(),
    modifier: Modifier = Modifier
) {
    require(items.isNotEmpty())

    val selectTabOffset by animateFloatAsState(state.selected.toFloat() / items.size)

    Box(
        modifier.background(color = ITDTheme.colors.surface, shape = CircleShape)
            .height(IntrinsicSize.Max)
            .padding(6.dp)
    ) {
        Box(
            Modifier.fillMaxWidth(1f / items.size)
                .graphicsLayer {
                    translationX = size.width * items.size * selectTabOffset
                }
                .fillMaxHeight()
                .background(color = ITDTheme.colors.border, shape = CircleShape)
        )
        Row(Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
            items.forEachIndexed { index, item ->
                Card(
                    onClick = { onClickItem(index) },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(8.dp).fillMaxHeight()
                        ) {
                            val color = if (index == state.selected) {
                                ITDTheme.colors.primary
                            } else {
                                ITDTheme.colors.onSurface
                            }
                            when (item) {
                                is TabItem.IconTabItem -> {
                                    Icon(
                                        item.icon,
                                        contentDescription = item.title,
                                        modifier = Modifier.size(24.dp),
                                        tint = color
                                    )
                                    VerticalSpacer(6.dp)
                                    Text(
                                        item.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = ITDTheme.typography.micro,
                                        color = color
                                    )
                                }

                                is TabItem.EmojiTabItem -> {
                                    Text(
                                        item.icon,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = ITDTheme.typography.body,
                                        color = color
                                    )
                                    VerticalSpacer(6.dp)
                                    Text(
                                        item.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = ITDTheme.typography.micro,
                                        color = color
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}