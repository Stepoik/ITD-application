package com.itd.app.features.feed.api.models

data class Attachment(
    val height: Int,
    val width: Int,
    val id: String,
    val type: AttachmentType?,
    val url: String
)

enum class AttachmentType {
    IMAGE,
    AUDIO
}
