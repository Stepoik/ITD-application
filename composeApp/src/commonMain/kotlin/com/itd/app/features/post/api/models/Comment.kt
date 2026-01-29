package com.itd.app.features.post.api.models

import com.itd.app.features.feed.api.models.Attachment
import com.itd.app.features.feed.api.models.Author
import kotlinx.datetime.LocalDateTime

data class Comment(
    val attachments: List<Attachment>,
    val author: Author,
    val content: String,
    val createdAt: LocalDateTime,
    val id: String,
    val isLiked: Boolean,
    val likesCount: Int,
    val repliesCount: Int,
    val replies: List<CommentReply>
)