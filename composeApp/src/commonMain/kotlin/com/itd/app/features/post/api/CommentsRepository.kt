package com.itd.app.features.post.api

import com.itd.app.features.post.api.models.Comment
import com.itd.app.features.post.api.models.CommentReply

interface CommentsRepository {
    suspend fun getComments(postId: String, offset: Int): Result<List<Comment>>

    suspend fun getReplies(commentId: String, offset: Int): Result<List<CommentReply>>

    suspend fun createReply(commentId: String, content: String, userId: String): Result<CommentReply>

    suspend fun createComment(postId: String, content: String): Result<Comment>
}