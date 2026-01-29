package com.itd.app.features.feed.data.dto.responses

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class GetPostResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val attachments: List<AttachmentDto>,
        val author: AuthorDto,
        val content: String,
        val createdAt: Instant,
        val id: String,
        val isLiked: Boolean,
        val isOwner: Boolean,
        val isReposted: Boolean,
        val isViewed: Boolean,
        val likesCount: Int,
        val originalPost: PostReferenceDto?,
        val repostsCount: Int,
        val viewsCount: Int,
        val wallRecipientId: String?,
        val commentsCount: Int
    )
}
