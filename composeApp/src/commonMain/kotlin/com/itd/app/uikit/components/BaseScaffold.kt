package com.itd.app.uikit.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itd.app.uikit.ITDTheme


@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = ITDTheme.colors.background,
        snackbarHost = snackbarHost,
        content = content
    )
}