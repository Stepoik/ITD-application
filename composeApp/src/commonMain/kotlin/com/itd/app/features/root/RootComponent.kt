package com.itd.app.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.itd.app.features.auth.ui.AuthComponent
import com.itd.app.features.home.HomeComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Auth(val component: AuthComponent) : Child()

        data class Home(val component: HomeComponent) : Child()
    }

    interface Factory {
        fun create(componentContext: ComponentContext): RootComponent
    }
}