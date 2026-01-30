package com.itd.app.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.core.Icon
import com.composeunstyled.Text
import com.itd.app.uikit.ITDTheme

@Composable
fun ITDButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier.clip(CircleShape).background(ITDTheme.colors.primary).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            style = ITDTheme.typography.bodyUi,
            color = ITDTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ITDFloatingActionButton(icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier.size(54.dp).clip(CircleShape).background(ITDTheme.colors.primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = ITDTheme.colors.onPrimary
        )
    }
}