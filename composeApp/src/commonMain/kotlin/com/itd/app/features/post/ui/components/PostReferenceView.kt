package com.itd.app.features.post.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.itd.app.features.common.ui.toAgoText
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.VerticalSpacer
import com.itd.app.uikit.icons.Icons
import com.itd.app.uikit.icons.Repost
import kotlinx.datetime.LocalDateTime

@Composable
fun PostReferenceView(postReference: PostReferenceVO, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = ITDTheme.colors.itemBg),
        border = BorderStroke(width = 1.dp, color = ITDTheme.colors.border),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            PostReferenceHeader(postReference.author, createdAt = postReference.createdAt)
            VerticalSpacer(12.dp)
            if (postReference.content.isNotEmpty()) {
                Text(postReference.content, style = ITDTheme.typography.bodyUi)
            }
            VerticalSpacer(8.dp)
            if (postReference.attachments.isNotEmpty()) {
                MosaicAttachments(postReference.attachments)
            }
        }
    }
}

@Composable
private fun PostReferenceHeader(
    author: AuthorVO,
    createdAt: LocalDateTime,
    modifier: Modifier = Modifier
) {
    val postText = createdAt.toAgoText()
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Repost,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = ITDTheme.colors.repost
        )
        Text(author.avatar, style = ITDTheme.typography.titleMedium)
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(author.displayName, style = ITDTheme.typography.bodyUi, fontWeight = FontWeight.SemiBold)
            Row {
                Text(author.username, style = ITDTheme.typography.small)
                Text(" ·", style = ITDTheme.typography.small)
                Text(postText, style = ITDTheme.typography.small)
            }
        }
    }
}