package com.itd.app.features.notifications.data.dto

import com.itd.app.core.serializers.InstantSpaceOffsetSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class NotificationDto(
    val actor: ActorDto,
    val createdAt: Instant,
    val id: String,
    val read: Boolean,
    val type: NotificationTypeDto,
    val preview: String?,
    val targetId: String?
)

@Serializable
data class ActorDto(
    val avatar: String,
    val displayName: String,
    val id: String,
    val username: String
)

enum class NotificationTypeDto {
    @SerialName("comment")
    COMMENT,

    @SerialName("follow")
    FOLLOW,

    @SerialName("like")
    LIKE,

    @SerialName("repost")
    REPOST,

    @SerialName("reply")
    REPLY
}
