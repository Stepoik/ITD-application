package com.itd.app.features.splash

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.EmptyState
import com.itd.app.core.ktor.TokenHolder
import com.itd.app.features.profile.api.ProfileRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class SplashComponentImpl(
    componentContext: ComponentContext,
    private val profileRepository: ProfileRepository,
    private val tokenHolder: TokenHolder,
    private val onNavigateHome: () -> Unit,
    private val onNavigateSignIn: () -> Unit,
) : SplashComponent,
    BaseComponent<EmptyState>(componentContext, serializer = EmptyState.serializer()) {
    init {
        componentScope.launch {
            profileRepository.getProfile("holop300")
            if (tokenHolder.tokens.firstOrNull() == null) {
                onNavigateSignIn()
            } else {
                onNavigateHome()
            }
        }
    }

    override fun initialState() = EmptyState

    class Factory : SplashComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            onNavigateHome: () -> Unit,
            onNavigateSignIn: () -> Unit,
        ): SplashComponent {
            return getKoin().get {
                parametersOf(
                    componentContext,
                    onNavigateHome,
                    onNavigateSignIn
                )
            }
        }
    }
}