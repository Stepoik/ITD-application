package com.itd.app.features.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.itd.app.features.auth.ui.AuthComponent
import com.itd.app.features.auth.ui.AuthScreen
import com.itd.app.features.home.HomeScreen

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
        }
    }
}