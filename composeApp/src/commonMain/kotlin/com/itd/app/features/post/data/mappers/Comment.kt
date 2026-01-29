package com.itd.app.features.post.data.mappers

import com.itd.app.features.feed.data.mappers.toDomain
import com.itd.app.features.post.api.models.Comment
import com.itd.app.features.post.api.models.CommentOrigAuthor
import com.itd.app.features.post.api.models.CommentReply
import com.itd.app.features.post.data.dto.CommentDto
import com.itd.app.features.post.data.dto.CommentOrigAuthorDto
import com.itd.app.features.post.data.dto.CommentReplyDto
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun CommentDto.toDomain(): Comment {
    return Comment(
        attachments = attachments.map { it.toDomain() },
        author = author.toDomain(),
        content = content,
        createdAt = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()),
        id = id,
        isLiked = isLiked,
        likesCount = likesCount,
        replies = replies.map { it.toDomain() },
        repliesCount = repliesCount
    )
}

fun CommentReplyDto.toDomain(): CommentReply {
    return CommentReply(
        attachments = attachments.map { it.toDomain() },
        author = author.toDomain(),
        content = content,
        createdAt = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()),
        id = id,
        isLiked = isLiked,
        likesCount = likesCount,
        replies = replies.map { it.toDomain() },
        repliesTo = replyTo?.toDomain()
    )
}

fun CommentOrigAuthorDto.toDomain(): CommentOrigAuthor {
    return CommentOrigAuthor(
        displayName = displayName,
        username = username ?: "",
        id = id
    )
}