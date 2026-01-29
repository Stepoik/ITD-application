package com.itd.app.features.common.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetTopClansResponse(
    val clans: List<ClanPreviewDto>
)

@Serializable
data class ClanPreviewDto(
    val avatar: String,
    val memberCount: Int
)
