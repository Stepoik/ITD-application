package com.itd.app.features.feed.api.models

import kotlinx.datetime.LocalDateTime

data class Post(
    val id: String,
    val author: Author,
    val commentsCount: Int,
    val content: String,
    val createdAt: LocalDateTime,
    val isLiked: Boolean,
    val isOwner: Boolean,
    val isReposted: Boolean,
    val isViewed: Boolean,
    val likesCount: Int,
    val originalPost: PostReference?,
    val repostsCount: Int,
    val viewsCount: Int,
    val wallRecipientId: String?,
    val attachments: List<Attachment>
)

data class PostReference(
    val id: String,
    val author: Author,
    val content: String,
    val createdAt: LocalDateTime,
    val attachments: List<Attachment>
)