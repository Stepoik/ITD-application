package com.itd.app.core.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

private const val BASE_URL = "${NetworkConstants.BASE_URL}/v1/auth"

class TokenRefresher(
    private val httpClient: HttpClient
) {
    suspend fun refresh(refreshToken: String): BearerTokens? {
        runCatching {
            httpClient.post("$BASE_URL/refresh") {
                header("cookie", "refresh_token=$refreshToken")
            }.body<Map<String, String>>()["accessToken"]!!
        }.onSuccess {
            return BearerTokens(accessToken = it, refreshToken = refreshToken)
        }
        return null
    }
}