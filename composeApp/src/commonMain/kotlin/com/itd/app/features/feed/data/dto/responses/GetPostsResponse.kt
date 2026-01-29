package com.itd.app.features.feed.data.dto.responses

import com.itd.app.core.serializers.InstantSpaceOffsetSerializer
import com.itd.app.features.feed.api.models.Author
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class GetPostsResponse(
    val data: GetPostsResponseData,
) {
    @Serializable
    data class GetPostsResponseData(
        val posts: List<PostDto>
    )
}
@Serializable
data class PostDto(
    val id: String,
    val author: AuthorDto,
    val commentsCount: Int,
    val content: String,
    @Serializable(with = InstantSpaceOffsetSerializer::class)
    val createdAt: Instant,
    val isLiked: Boolean,
    val isOwner: Boolean,
    val isReposted: Boolean,
    val isViewed: Boolean,
    val likesCount: Int,
    val originalPost: PostReferenceDto?,
    val repostsCount: Int,
    val viewsCount: Int,
    val wallRecipientId: String?,
    val attachments: List<AttachmentDto>
)

@Serializable
data class AuthorDto(
    val id: String,
    val avatar: String,
    val displayName: String,
    val username: String?,
    val verified: Boolean
)

@Serializable
data class AttachmentDto(
    val height: Int?,
    val width: Int?,
    val id: String,
    val type: AttachmentTypeDto?,
    val url: String?
)

enum class AttachmentTypeDto {
    @SerialName("audio")
    AUDIO,
    @SerialName("image")
    IMAGE,
}

@Serializable
data class PostReferenceDto(
    val id: String,
    val content: String,
    val author: AuthorDto,
    val attachments: List<AttachmentDto>,
    @Serializable(with = InstantSpaceOffsetSerializer::class)
    val createdAt: Instant,
    val isDeleted: Boolean
)