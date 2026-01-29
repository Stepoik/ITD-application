package com.itd.app.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.itd.app.uikit.ITDTheme

@Composable
fun TabRow(
    tabs: List<String>,
    selected: Int,
    onChangeTab: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        DefaultHorizontalDivider(
            1.dp,
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            tabs.forEachIndexed { index, title ->
                TabItem(
                    title,
                    isSelected = index == selected,
                    onClick = { onChangeTab(index) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        val textWeight = if (isSelected) {
            FontWeight.Bold
        } else {
            FontWeight.Normal
        }
        Text(text, fontWeight = textWeight, modifier = Modifier.padding(16.dp))
        if (isSelected) {
            Box(
                Modifier.height(1.dp).fillMaxWidth().background(ITDTheme.colors.primary)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}