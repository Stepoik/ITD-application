package com.itd.app.uikit.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.itd.app.uikit.ITDTheme

@Composable
fun DefaultHorizontalDivider(thickness: Dp, modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier, thickness = thickness, color = ITDTheme.colors.divider)
}

fun LazyListScope.horizontalDivider(thickness: Dp, modifier: Modifier = Modifier) {
    item {
        DefaultHorizontalDivider(thickness = thickness, modifier = modifier)
    }
}