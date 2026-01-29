package com.itd.app.features.common.data

import com.itd.app.core.ktor.NetworkConstants
import com.itd.app.features.common.api.TrendsRepository
import com.itd.app.features.common.api.models.HashtagPreview
import com.itd.app.features.common.data.dto.GetHashtagTrendsResponse
import com.itd.app.features.common.data.mappers.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TrendsRepositoryImpl(
    private val httpClient: HttpClient
) : TrendsRepository {
    override suspend fun getTopHashtags(): Result<List<HashtagPreview>> {
        return runCatching {
            httpClient.get("${NetworkConstants.BASE_URL}/hashtags/trending") {
                parameter("limit", 10)
            }.body<GetHashtagTrendsResponse>().data.hashtags.map { it.toDomain() }
        }
    }
}