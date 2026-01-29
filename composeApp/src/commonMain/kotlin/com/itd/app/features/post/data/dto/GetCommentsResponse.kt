package com.itd.app.features.post.data.dto

import com.itd.app.core.serializers.InstantSpaceOffsetSerializer
import com.itd.app.features.feed.data.dto.responses.AttachmentDto
import com.itd.app.features.feed.data.dto.responses.AuthorDto
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class GetCommentsResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val comments: List<CommentDto>
    )
}

@Serializable
data class CommentDto(
    val attachments: List<AttachmentDto>,
    val author: AuthorDto,
    val content: String,
    @Serializable(with = InstantSpaceOffsetSerializer::class)
    val createdAt: Instant,
    val id: String,
    val isLiked: Boolean,
    val likesCount: Int,
    val replies: List<CommentReplyDto> = listOf(),
    val repliesCount: Int
)

@Serializable
data class CommentReplyDto(
    val attachments: List<AttachmentDto>,
    val author: AuthorDto,
    val content: String,
    @Serializable(with = InstantSpaceOffsetSerializer::class)
    val createdAt: Instant,
    val id: String,
    val isLiked: Boolean,
    val likesCount: Int,
    val replies: List<CommentReplyDto> = listOf(),
    val replyTo: CommentOrigAuthorDto?
)

@Serializable
data class CommentOrigAuthorDto(
    val displayName: String,
    val id: String,
    val username: String?
)