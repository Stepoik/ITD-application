package com.itd.app.uikit.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(dp: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier.height(dp))
}

@Composable
fun HorizontalSpacer(dp: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier.width(dp))
}

fun LazyListScope.verticalSpacer(dp: Dp) {
    item {
        VerticalSpacer(dp)
    }
}