package com.itd.app.features.post.data

import com.itd.app.core.ktor.NetworkConstants
import com.itd.app.features.post.api.CommentsRepository
import com.itd.app.features.post.api.models.Comment
import com.itd.app.features.post.api.models.CommentReply
import com.itd.app.features.post.data.dto.CommentDto
import com.itd.app.features.post.data.dto.CommentReplyDto
import com.itd.app.features.post.data.dto.GetCommentsResponse
import com.itd.app.features.post.data.dto.GetRepliesResponse
import com.itd.app.features.post.data.mappers.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class CommentsRepositoryImpl(
    private val httpClient: HttpClient
) : CommentsRepository {
    override suspend fun getComments(postId: String, offset: Int): Result<List<Comment>> {
        return runCatching {
            httpClient.get("${NetworkConstants.BASE_URL}/posts/$postId/comments") {
                parameter("limit", PAGE_SIZE)
                parameter("sort", "popular")
                if (offset != 0) {
                    parameter("cursor", offset)
                }
            }.body<GetCommentsResponse>().data.comments.map { it.toDomain() }
        }
    }

    override suspend fun getReplies(
        commentId: String,
        offset: Int
    ): Result<List<CommentReply>> {
        val page = offset / PAGE_SIZE + 1
        return runCatching {
            httpClient.get("${NetworkConstants.BASE_URL}/comments/$commentId/replies") {
                parameter("limit", PAGE_SIZE)
                parameter("sort", "oldest")
                parameter("page", page)
            }.body<GetRepliesResponse>().data.replies.map { it.toDomain() }
        }
    }

    override suspend fun createReply(
        commentId: String,
        content: String,
        userId: String
    ): Result<CommentReply> {
        return runCatching {
            httpClient.post("${NetworkConstants.BASE_URL}/comments/$commentId/replies") {
                setBody(mapOf("content" to content, "replyToUserId" to userId))
            }.body<CommentReplyDto>().toDomain()
        }
    }

    override suspend fun createComment(
        postId: String,
        content: String
    ): Result<Comment> {
        return runCatching {
            httpClient.post("${NetworkConstants.BASE_URL}/posts/$postId/comments") {
                setBody(mapOf("content" to content))
            }.body<CommentDto>().toDomain()
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}