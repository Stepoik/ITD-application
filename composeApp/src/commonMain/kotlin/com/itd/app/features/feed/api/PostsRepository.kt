package com.itd.app.features.feed.api

import com.itd.app.features.feed.api.models.HashtagPosts
import com.itd.app.features.feed.api.models.LikeStatus
import com.itd.app.features.feed.api.models.Post
import com.itd.app.features.feed.api.models.PostsType
import kotlinx.datetime.LocalDateTime

interface PostsRepository {
    suspend fun getPosts(offset: Int, postsType: PostsType): Result<List<Post>>

    suspend fun getPostsByUser(username: String, offset: LocalDateTime?): Result<List<Post>>

    suspend fun getPostsByUserLiked(username: String, offset: LocalDateTime?): Result<List<Post>>

    suspend fun likePost(postId: String, like: Boolean): Result<LikeStatus>

    suspend fun getPostById(postId: String): Result<Post>

    suspend fun getPostsByHashtag(hashtag: String, lastPostId: String?): Result<HashtagPosts>
}