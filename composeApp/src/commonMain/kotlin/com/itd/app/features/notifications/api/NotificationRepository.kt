package com.itd.app.features.notifications.api

import com.itd.app.features.notifications.api.models.Notification

interface NotificationRepository {
    suspend fun getNotifications(offset: Int): Result<List<Notification>>
}