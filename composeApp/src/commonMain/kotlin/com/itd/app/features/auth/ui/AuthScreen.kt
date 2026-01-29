package com.itd.app.features.auth.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itd.app.uikit.components.BaseScaffold

@Composable
fun AuthScreen(component: AuthComponent) {
    BaseScaffold {
        WebView(onToken = component::onGotToken, Modifier.fillMaxSize())
    }
}

@Composable
expect fun WebView(
    onToken: (accessToken: String, refreshToken: String) -> Unit,
    modifier: Modifier = Modifier
)