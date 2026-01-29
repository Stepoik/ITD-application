package com.itd.app.features.splash

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.core.decompose.EmptyState

interface SplashComponent : Component<EmptyState> {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onNavigateHome: () -> Unit,
            onNavigateSignIn: () -> Unit,
        ): SplashComponent
    }
}