package com.itd.app.features.feed.ui.list.mappers

import com.itd.app.features.feed.api.models.Attachment
import com.itd.app.features.post.ui.components.AttachmentVO

fun Attachment.toVO(): AttachmentVO {
    return AttachmentVO(
        imageUrl = url,
        width = width,
        height = height,
        type = type
    )
}