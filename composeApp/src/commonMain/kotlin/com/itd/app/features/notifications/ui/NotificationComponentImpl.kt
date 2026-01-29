package com.itd.app.features.notifications.ui

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.notifications.api.NotificationRepository
import com.itd.app.features.notifications.api.models.NotificationType
import com.itd.app.features.notifications.ui.mappers.toVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class NotificationComponentImpl(
    componentContext: ComponentContext,
    private val notificationRepository: NotificationRepository
) : NotificationComponent, BaseComponent<NotificationScreenState>(
    componentContext,
    NotificationScreenState.serializer()
) {
    init {
        onLoadNext()
    }

    override fun initialState() = NotificationScreenState()

    override fun onLoadNext() {
        val state = state.value
        if (state.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch(Dispatchers.Default) {
            notificationRepository.getNotifications(state.allNotifications.size)
                .onSuccess { newNotifications ->
                    updateState {
                        val notifications = it.allNotifications + newNotifications.map { it.toVO() }
                        val mentions = notifications.filter { it.type == NotificationType.REPLY }
                        it.copy(allNotifications = notifications, mentions = mentions, isLoading = false)
                    }
                }
        }
    }

    class Factory : NotificationComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext): NotificationComponent {
            return getKoin().get { parametersOf(componentContext) }
        }
    }
}