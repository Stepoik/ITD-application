package com.itd.app.features.auth.ui

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.EmptyState
import com.itd.app.core.ktor.BearerTokens
import com.itd.app.core.ktor.TokenHolder
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class AuthComponentImpl(
    componentContext: ComponentContext,
    private val tokenHolder: TokenHolder,
    private val onAuthorized: () -> Unit
) : AuthComponent, BaseComponent<EmptyState>(componentContext, EmptyState.serializer()) {
    override fun initialState() = EmptyState

    override fun onGotToken(accessToken: String, refreshToken: String) {
        componentScope.launch {
            tokenHolder.setTokens(BearerTokens(accessToken = accessToken, refreshToken = refreshToken))
            onAuthorized()
        }
    }

    class Factory : AuthComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext, onAuthorized: () -> Unit): AuthComponent {
            return getKoin().get { parametersOf(componentContext, onAuthorized) }
        }
    }
}