package com.itd.app.features.notifications.ui.mappers

import com.itd.app.features.notifications.api.models.Notification
import com.itd.app.features.notifications.api.models.NotificationAuthor
import com.itd.app.features.notifications.ui.NotificationAuthorVO
import com.itd.app.features.notifications.ui.NotificationVO

fun Notification.toVO(): NotificationVO {
    return NotificationVO(
        author = author.toVO(),
        createdAt = createdAt,
        id = id,
        read = read,
        type = type,
        preview = preview,
        targetId = targetId
    )
}

fun NotificationAuthor.toVO(): NotificationAuthorVO {
    return NotificationAuthorVO(
        avatar = avatar,
        displayName = displayName,
        id = id,
        username = username
    )
}