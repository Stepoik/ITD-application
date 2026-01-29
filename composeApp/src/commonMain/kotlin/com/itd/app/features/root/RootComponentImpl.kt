package com.itd.app.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.EmptyState
import com.itd.app.features.auth.ui.AuthComponent
import com.itd.app.features.home.HomeComponent
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class RootComponentImpl(
    componentContext: ComponentContext,
    private val homeComponentFactory: HomeComponent.Factory,
    private val authComponentFactory: AuthComponent.Factory
) : RootComponent, BaseComponent<EmptyState>(componentContext, EmptyState.serializer()) {
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Auth(),
        childFactory = ::createChild
    )

    override fun initialState() = EmptyState

    private fun createChild(config: Config, context: ComponentContext): RootComponent.Child {
        return when (config) {
            is Config.Home -> {
                RootComponent.Child.Home(homeComponentFactory.create(context))
            }

            is Config.Auth -> {
                RootComponent.Child.Auth(
                    authComponentFactory.create(
                        componentContext = context,
                        onAuthorized = {
                            navigation.replaceAll(Config.Home())
                        }
                    )
                )
            }
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        class Auth : Config()

        @Serializable
        class Home : Config()
    }

    class Factory : RootComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext): RootComponent {
            return getKoin().get { parametersOf(componentContext) }
        }
    }
}