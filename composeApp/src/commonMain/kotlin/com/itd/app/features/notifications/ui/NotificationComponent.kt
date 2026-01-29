package com.itd.app.features.notifications.ui

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component

interface NotificationComponent : Component<NotificationScreenState> {
    fun onLoadNext()

    interface Factory {
        fun create(componentContext: ComponentContext): NotificationComponent
    }
}