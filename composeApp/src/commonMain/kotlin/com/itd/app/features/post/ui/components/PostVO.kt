package com.itd.app.features.post.ui.components

import com.itd.app.features.feed.api.models.AttachmentType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PostVO(
    val id: String,
    val author: AuthorVO,
    val commentsCount: Int,
    val content: String,
    val createdAt: LocalDateTime,
    val isLiked: Boolean,
    val isOwner: Boolean,
    val isReposted: Boolean,
    val isViewed: Boolean,
    val likesCount: Int,
    val originalPost: PostReferenceVO?,
    val repostsCount: String,
    val viewsCount: String,
    val wallRecipientId: String?,
    val attachments: List<AttachmentVO>
)

@Serializable
data class PostReferenceVO(
    val id: String,
    val author: AuthorVO,
    val content: String,
    val attachments: List<AttachmentVO>,
    val createdAt: LocalDateTime
)

@Serializable
data class AttachmentVO(
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val type: AttachmentType?
)

@Serializable
data class AuthorVO(
    val id: String,
    val avatar: String,
    val displayName: String,
    val username: String,
    val isVerified: Boolean
)