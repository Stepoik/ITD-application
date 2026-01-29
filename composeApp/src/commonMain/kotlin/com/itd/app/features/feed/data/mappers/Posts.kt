package com.itd.app.features.feed.data.mappers

import com.itd.app.features.feed.api.models.Post
import com.itd.app.features.feed.api.models.PostReference
import com.itd.app.features.feed.data.dto.responses.GetPostResponse
import com.itd.app.features.feed.data.dto.responses.PostDto
import com.itd.app.features.feed.data.dto.responses.PostReferenceDto
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        author = author.toDomain(),
        commentsCount = commentsCount,
        content = content,
        createdAt = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()),
        isLiked = isLiked,
        isOwner = isOwner,
        isReposted = isReposted,
        isViewed = isViewed,
        likesCount = likesCount,
        originalPost = originalPost?.toDomain(),
        repostsCount = repostsCount,
        viewsCount = viewsCount,
        wallRecipientId = wallRecipientId,
        attachments = attachments.map { it.toDomain() }
    )
}

fun PostReferenceDto.toDomain(): PostReference {
    return PostReference(
        id = id,
        author = author.toDomain(),
        content = content,
        createdAt = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()),
        attachments = attachments.map { it.toDomain() }
    )
}

fun GetPostResponse.Data.toDomain(): Post {
    return Post(
        id = id,
        author = author.toDomain(),
        commentsCount = commentsCount,
        content = content,
        createdAt = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()),
        isLiked = isLiked,
        isOwner = isOwner,
        isReposted = isReposted,
        isViewed = isViewed,
        likesCount = likesCount,
        originalPost = originalPost?.toDomain(),
        repostsCount = repostsCount,
        viewsCount = viewsCount,
        wallRecipientId = wallRecipientId,
        attachments = attachments.map { it.toDomain() }
    )
}