package com.itd.app.features.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.itd.app.features.auth.ui.AuthComponent
import com.itd.app.features.auth.ui.AuthScreen
import com.itd.app.features.home.HomeScreen
import com.itd.app.uikit.ITDTheme

@Composable
fun Root(component: RootComponent) {
    Children(component.stack) {
        when (val instance = it.instance) {
            is RootComponent.Child.Home -> {
                HomeScreen(instance.component)
            }

            is RootComponent.Child.Auth -> {
                AuthScreen(instance.component)
            }

            is RootComponent.Child.Splash -> {
                Box(Modifier.fillMaxSize().background(ITDTheme.colors.background))
            }
        }
    }
}