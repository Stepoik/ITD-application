package com.itd.app.features.notifications.api.models

import kotlinx.datetime.LocalDateTime

data class Notification(
    val author: NotificationAuthor,
    val createdAt: LocalDateTime,
    val id: String,
    val read: Boolean,
    val type: NotificationType,
    val preview: String?,
    val targetId: String?
)

enum class NotificationType {
    COMMENT,
    FOLLOW,
    LIKE,
    REPOST,
    REPLY
}