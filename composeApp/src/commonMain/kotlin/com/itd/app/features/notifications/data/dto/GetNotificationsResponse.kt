package com.itd.app.features.notifications.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetNotificationsResponse(
    val notifications: List<NotificationDto>
)
