package com.itd.app.features.post.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MosaicAttachments(attachments: List<AttachmentVO>, modifier: Modifier = Modifier) {
    val aspectRatioModifier = if (attachments.size == 1) {
        modifier.aspectRatio(attachments[0].width / attachments[0].height.toFloat())
    } else {
        modifier.aspectRatio(0.86f / 1)
    }
    Box(aspectRatioModifier.clip(RoundedCornerShape(16.dp))) {
        MosaicLayout(attachments.size) { index, modifier ->
            val contentScale = if (attachments.size > 1) {
                ContentScale.Crop
            } else {
                ContentScale.Fit
            }
            AsyncImage(
                attachments[index].imageUrl,
                contentScale = contentScale,
                contentDescription = null,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun MosaicLayout(
    count: Int,
    modifier: Modifier = Modifier,
    gap: Dp = 2.dp,
    cornerRadius: Dp = 12.dp,
    showOverflowBadge: Boolean = true,
    item: @Composable (index: Int, modifier: Modifier) -> Unit
) {
    val shown = minOf(count, 4)
    val overflow = (count - 4).coerceAtLeast(0)

    @Composable
    fun cell(index: Int, m: Modifier) {
        Box(m.clip(RoundedCornerShape(cornerRadius))) {
            item(index, Modifier.fillMaxSize())
            if (showOverflowBadge && index == 3 && overflow > 0) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.45f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+$overflow",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    when (shown) {
        0 -> Unit

        1 -> {
            cell(0, modifier.fillMaxSize())
        }

        2 -> {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                cell(0, Modifier.weight(1f).fillMaxHeight())
                cell(1, Modifier.weight(1f).fillMaxHeight())
            }
        }

        3 -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(gap)
            ) {
                Row(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gap)
                ) {
                    cell(0, Modifier.weight(1f).fillMaxHeight())
                    cell(1, Modifier.weight(1f).fillMaxHeight())
                }
                cell(2, Modifier.weight(1f).fillMaxWidth())
            }
        }

        else -> { // 4+
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(gap)
            ) {
                Row(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gap)
                ) {
                    cell(0, Modifier.weight(1f).fillMaxHeight())
                    cell(1, Modifier.weight(1f).fillMaxHeight())
                }
                Row(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gap)
                ) {
                    cell(2, Modifier.weight(1f).fillMaxHeight())
                    cell(3, Modifier.weight(1f).fillMaxHeight())
                }
            }
        }
    }
}