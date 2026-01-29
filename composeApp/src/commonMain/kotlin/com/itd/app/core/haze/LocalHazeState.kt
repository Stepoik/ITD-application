package com.itd.app.core.haze

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HazeProvider(
    content: @Composable () -> Unit
) {
    val screensHazeState = rememberHazeState()
    CompositionLocalProvider(
        LocalHazeState provides screensHazeState
    ) {
        content()
    }
}

val LocalHazeState = staticCompositionLocalOf<HazeState> { error("no default impl") }