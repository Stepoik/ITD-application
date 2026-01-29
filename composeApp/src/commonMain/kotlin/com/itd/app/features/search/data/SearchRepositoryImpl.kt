package com.itd.app.features.search.data

import com.itd.app.core.ktor.NetworkConstants
import com.itd.app.features.search.api.SearchRepository
import com.itd.app.features.search.api.models.SearchResult
import com.itd.app.features.search.data.dto.SearchResponse
import com.itd.app.features.search.data.mappers.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchRepositoryImpl(
    private val httpClient: HttpClient
) : SearchRepository {
    override suspend fun search(text: String): Result<SearchResult> {
        return runCatching {
            httpClient.get("${NetworkConstants.BASE_URL}/search") {
                parameter("q", text)
                parameter("userLimit", 5)
                parameter("hashtagLimit", 5)
            }.body<SearchResponse>().toDomain()
        }
    }
}