package com.itd.app.features.feed.data.mappers

import com.itd.app.features.feed.api.models.Attachment
import com.itd.app.features.feed.api.models.AttachmentType
import com.itd.app.features.feed.data.dto.responses.AttachmentDto
import com.itd.app.features.feed.data.dto.responses.AttachmentTypeDto

fun AttachmentDto.toDomain(): Attachment {
    return Attachment(
        height = height ?: 0,
        width = width ?: 0,
        type = type?.toDomain(),
        url = url ?: "",
        id = id
    )
}

fun AttachmentTypeDto.toDomain(): AttachmentType {
    return when (this) {
        AttachmentTypeDto.AUDIO -> AttachmentType.AUDIO
        AttachmentTypeDto.IMAGE -> AttachmentType.IMAGE
    }
}