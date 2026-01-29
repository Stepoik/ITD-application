package com.itd.app.features.notifications.data.mappers

import com.itd.app.features.notifications.api.models.Notification
import com.itd.app.features.notifications.api.models.NotificationAuthor
import com.itd.app.features.notifications.api.models.NotificationType
import com.itd.app.features.notifications.data.dto.ActorDto
import com.itd.app.features.notifications.data.dto.NotificationDto
import com.itd.app.features.notifications.data.dto.NotificationTypeDto
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NotificationDto.toDomain(): Notification {
    return Notification(
        author = actor.toDomain(),
        createdAt = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()),
        id = id,
        read = read,
        type = type.toDomain(),
        preview = preview,
        targetId = targetId
    )
}

fun ActorDto.toDomain(): NotificationAuthor {
    return NotificationAuthor(
        avatar = avatar,
        displayName = displayName,
        id = id,
        username = username
    )
}

fun NotificationTypeDto.toDomain(): NotificationType {
    return when (this) {
        NotificationTypeDto.LIKE -> NotificationType.LIKE
        NotificationTypeDto.REPLY -> NotificationType.REPLY
        NotificationTypeDto.FOLLOW -> NotificationType.FOLLOW
        NotificationTypeDto.REPOST -> NotificationType.REPOST
        NotificationTypeDto.COMMENT -> NotificationType.COMMENT
    }
}