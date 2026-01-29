package com.itd.app.features.feed.ui.list.mappers

import com.itd.app.features.feed.api.models.AttachmentType
import com.itd.app.features.feed.api.models.Post
import com.itd.app.features.feed.api.models.PostReference
import com.itd.app.features.post.ui.components.PostReferenceVO
import com.itd.app.features.post.ui.components.PostVO
import kotlin.math.floor

fun Post.toVO(): PostVO {
    return PostVO(
        id = id,
        author = author.toVO(),
        commentsCount = commentsCount,
        content = content,
        createdAt = createdAt,
        isLiked = isLiked,
        isOwner = isOwner,
        isReposted = isReposted,
        isViewed = isViewed,
        likesCount = likesCount,
        repostsCount = repostsCount.formatThousand(),
        viewsCount = viewsCount.formatThousand(),
        wallRecipientId = wallRecipientId,
        originalPost = originalPost?.toVO(),
        attachments = attachments.map { it.toVO() }.filter { it.type == AttachmentType.IMAGE }
    )
}

fun PostReference.toVO(): PostReferenceVO {
    return PostReferenceVO(
        id = id,
        author = author.toVO(),
        attachments = attachments.map { it.toVO() },
        content = content,
        createdAt = createdAt
    )
}

fun Int.formatThousand(): String {
    if (this < 1_000) return this.toString()

    val k = this / 1_000.0
    val rounded = floor(k * 10) / 10   // 1 знак после запятой, без округления вверх

    return if (rounded % 1.0 == 0.0) {
        "${rounded.toInt()}К"
    } else {
        "${rounded}К"
    }
}