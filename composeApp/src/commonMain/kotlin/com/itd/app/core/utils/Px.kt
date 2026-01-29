package com.itd.app.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val Int.px: TextUnit
    @Composable
    get() {
        val density = LocalDensity.current
        return with(density) {
            this@px.dp.toSp()
        }
    }