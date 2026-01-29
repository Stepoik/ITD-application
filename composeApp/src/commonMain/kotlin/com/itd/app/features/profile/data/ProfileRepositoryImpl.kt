package com.itd.app.features.profile.data

import com.itd.app.core.ktor.NetworkConstants
import com.itd.app.features.profile.api.ProfileRepository
import com.itd.app.features.profile.api.models.Profile
import com.itd.app.features.profile.api.models.ProfilePreview
import com.itd.app.features.profile.data.dto.GetProfilePreviewResponse
import com.itd.app.features.profile.data.dto.ProfileDto
import com.itd.app.features.profile.data.mappers.toProfile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProfileRepositoryImpl(
    private val httpClient: HttpClient
) : ProfileRepository {
    override suspend fun getMe(): Result<ProfilePreview> {
        return runCatching {
            httpClient.get("${NetworkConstants.BASE_URL}/profile")
                .body<GetProfilePreviewResponse>()
                .user
                .toProfile()
        }
    }

    override suspend fun getProfile(username: String): Result<Profile> {
        return runCatching {
            httpClient.get("${NetworkConstants.BASE_URL}/users/$username")
                .body<ProfileDto>()
                .toProfile()
        }
    }
}