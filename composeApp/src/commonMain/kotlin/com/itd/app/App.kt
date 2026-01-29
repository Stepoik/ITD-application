package com.itd.app

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.itd.app.features.home.HomeScreen
import com.itd.app.features.root.Root
import com.itd.app.features.root.RootComponent
import com.itd.app.uikit.ITDTheme

@Composable
fun App(rootComponent: RootComponent) {
    ITDTheme {
        Root(rootComponent)
    }
}