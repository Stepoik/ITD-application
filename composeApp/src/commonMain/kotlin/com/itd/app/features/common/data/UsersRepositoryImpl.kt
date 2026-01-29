package com.itd.app.features.common.data

import com.itd.app.core.ktor.NetworkConstants
import com.itd.app.features.common.api.UsersRepository
import com.itd.app.features.common.api.models.ClanPreview
import com.itd.app.features.common.api.models.UserPreview
import com.itd.app.features.common.data.dto.GetTopClansResponse
import com.itd.app.features.common.data.dto.GetWhoToFollowResponse
import com.itd.app.features.common.data.mappers.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "${NetworkConstants.BASE_URL}/users"

class UsersRepositoryImpl(
    private val httpClient: HttpClient
) : UsersRepository {
    override suspend fun getWhoToFollow(): Result<List<UserPreview>> {
        return runCatching {
            httpClient.get("$BASE_URL/suggestions/who-to-follow")
                .body<GetWhoToFollowResponse>().users.map { it.toDomain() }
        }
    }

    override suspend fun getTopClans(): Result<List<ClanPreview>> {
        return runCatching {
            httpClient.get("$BASE_URL/stats/top-clans")
                .body<GetTopClansResponse>().clans.map { it.toDomain() }
        }
    }
}