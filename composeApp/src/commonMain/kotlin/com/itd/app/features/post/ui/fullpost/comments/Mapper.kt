package com.itd.app.features.post.ui.fullpost.comments

import com.itd.app.features.feed.api.models.AttachmentType
import com.itd.app.features.feed.ui.list.mappers.toVO
import com.itd.app.features.post.api.models.Comment
import com.itd.app.features.post.api.models.CommentOrigAuthor
import com.itd.app.features.post.api.models.CommentReply
import com.itd.app.features.post.ui.components.AuthorVO

fun Comment.toVO(): CommentVO {
    val showMore = if (repliesCount > replies.size) {
        ShowMoreVO.Count(repliesCount - replies.size)
    } else null
    return CommentVO(
        attachments = attachments.map { it.toVO() }.filter { it.type == AttachmentType.IMAGE },
        author = author.toVO(),
        content = content,
        createdAt = createdAt,
        id = id,
        isLiked = isLiked,
        likesCount = likesCount,
        replies = replies.map { it.toVO() },
        repliesTo = null,
        showMore = showMore,
        repliesCount = repliesCount,
        answerField = null
    )
}

fun CommentReply.toVO(): CommentVO {
    return CommentVO(
        repliesTo = repliesTo?.toVO(),
        attachments = attachments.map { it.toVO() }.filter { it.type == AttachmentType.IMAGE },
        author = author.toVO(),
        content = content,
        createdAt = createdAt,
        id = id,
        isLiked = isLiked,
        likesCount = likesCount,
        replies = replies.map { it.toVO() },
        showMore = null,
        repliesCount = 0,
        answerField = null
    )
}

fun CommentOrigAuthor.toVO(): RepliesToVO {
    return RepliesToVO(
        username = username,
        userId = id,
        displayName = "@$displayName"
    )
}

fun AuthorVO.toRepliesTo(): RepliesToVO {
    return RepliesToVO(
        username = username,
        userId = id,
        displayName = displayName
    )
}