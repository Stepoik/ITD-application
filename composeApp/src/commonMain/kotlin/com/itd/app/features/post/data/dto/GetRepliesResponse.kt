package com.itd.app.features.post.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetRepliesResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val replies: List<CommentReplyDto>
    )
}
