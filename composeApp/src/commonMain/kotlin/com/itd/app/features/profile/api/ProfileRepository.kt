package com.itd.app.features.profile.api

import com.itd.app.features.profile.api.models.Profile
import com.itd.app.features.profile.api.models.ProfilePreview

interface ProfileRepository {
    suspend fun getMe(): Result<ProfilePreview>

    suspend fun getProfile(username: String): Result<Profile>
}