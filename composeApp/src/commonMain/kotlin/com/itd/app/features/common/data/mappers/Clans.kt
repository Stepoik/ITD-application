package com.itd.app.features.common.data.mappers

import com.itd.app.features.common.api.models.ClanPreview
import com.itd.app.features.common.data.dto.ClanPreviewDto

fun ClanPreviewDto.toDomain(): ClanPreview {
    return ClanPreview(avatar = avatar, membersCount = memberCount)
}