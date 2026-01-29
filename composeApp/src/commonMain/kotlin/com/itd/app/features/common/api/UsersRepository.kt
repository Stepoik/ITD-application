package com.itd.app.features.common.api

import com.itd.app.features.common.api.models.ClanPreview
import com.itd.app.features.common.api.models.UserPreview

interface UsersRepository {
    suspend fun getWhoToFollow(): Result<List<UserPreview>>

    suspend fun getTopClans(): Result<List<ClanPreview>>
}