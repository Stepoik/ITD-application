package com.itd.app.features.feed.data

import com.itd.app.core.ktor.NetworkConstants
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.api.models.HashtagPosts
import com.itd.app.features.feed.api.models.LikeStatus
import com.itd.app.features.feed.api.models.Post
import com.itd.app.features.feed.api.models.PostsType
import com.itd.app.features.feed.data.dto.responses.GetHashtagPostsResponse
import com.itd.app.features.feed.data.dto.responses.GetPostResponse
import com.itd.app.features.feed.data.dto.responses.GetPostsResponse
import com.itd.app.features.feed.data.dto.responses.LikeStatusResponse
import com.itd.app.features.feed.data.mappers.toDomain
import com.itd.app.features.feed.data.mappers.toNetworkType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.util.AttributeKey
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.json.Json

private const val BASE_URL = "${NetworkConstants.BASE_URL}/posts"

class PostsRepositoryImpl(
    private val httpClient: HttpClient
) : PostsRepository {
    override suspend fun getPosts(offset: Int, postsType: PostsType): Result<List<Post>> {
        val cursor = offset / PAGE_SIZE
        return runCatching {
            val body = httpClient.get(BASE_URL) {
                parameter("limit", PAGE_SIZE)
                parameter("cursor", cursor)
                parameter("tab", postsType.toNetworkType())
            }.body<GetPostsResponse>()
            body.data.posts.map { it.toDomain() }
        }
    }

    override suspend fun getPostsByUser(
        username: String,
        offset: LocalDateTime?
    ): Result<List<Post>> {
        val instant = offset?.toInstant(TimeZone.currentSystemDefault())
        return runCatching {
            val body = httpClient.get("$BASE_URL/user/$username") {
                parameter("limit", PAGE_SIZE)
                parameter("sort", "new")
                instant?.let {
                    parameter("cursor", instant)
                }
            }.body<GetPostsResponse>()
            body.data.posts.map { it.toDomain() }
        }
    }

    override suspend fun getPostsByUserLiked(
        username: String,
        offset: LocalDateTime?
    ): Result<List<Post>> {
        val instant = offset?.toInstant(TimeZone.currentSystemDefault())
        return runCatching {
            val body = httpClient.get("$BASE_URL/user/$username/liked") {
                parameter("limit", PAGE_SIZE)
                parameter("sort", "new")
                instant?.let {
                    parameter("cursor", instant)
                }
            }.body<GetPostsResponse>()
            body.data.posts.map { it.toDomain() }
        }
    }

    override suspend fun likePost(postId: String, like: Boolean): Result<LikeStatus> {
        return runCatching {
            if (like) {
                httpClient.post("$BASE_URL/$postId/like")
            } else {
                httpClient.delete("$BASE_URL/$postId/like")
            }.body<LikeStatusResponse>().toDomain()
        }
    }

    override suspend fun getPostById(postId: String): Result<Post> {
        return runCatching {
            httpClient.get("$BASE_URL/$postId")
                .body<GetPostResponse>().data.toDomain()
        }
    }

    override suspend fun getPostsByHashtag(
        hashtag: String,
        lastPostId: String?
    ): Result<HashtagPosts> {
        return runCatching {
            val body = httpClient.get("${NetworkConstants.BASE_URL}/hashtags/$hashtag/posts") {
                parameter("limit", PAGE_SIZE)
                lastPostId?.let {
                    parameter("cursor", lastPostId)
                }
            }.body<GetHashtagPostsResponse>()
            HashtagPosts(
                posts = body.data.posts.map { it.toDomain() },
                postsCount = body.data.hashtag.postsCount
            )
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}