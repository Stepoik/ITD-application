package com.itd.app.features.auth.ui

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.core.decompose.EmptyState

interface AuthComponent : Component<EmptyState> {
    fun onGotToken(accessToken: String, refreshToken: String)

    interface Factory {
        fun create(componentContext: ComponentContext, onAuthorized: () -> Unit): AuthComponent
    }
}