package com.itd.app.features.notifications.ui

import com.itd.app.core.decompose.UIState
import com.itd.app.features.notifications.api.models.NotificationType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NotificationScreenState(
    val isLoading: Boolean = false,
    val allNotifications: List<NotificationVO> = listOf(),
    val mentions: List<NotificationVO> = listOf()
): UIState

@Serializable
data class NotificationVO(
    val author: NotificationAuthorVO,
    val createdAt: LocalDateTime,
    val id: String,
    val read: Boolean,
    val type: NotificationType,
    val preview: String?,
    val targetId: String?
)

@Serializable
data class NotificationAuthorVO(
    val avatar: String,
    val displayName: String,
    val id: String,
    val username: String
)