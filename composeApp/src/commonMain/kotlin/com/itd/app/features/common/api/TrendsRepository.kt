package com.itd.app.features.common.api

import com.itd.app.features.common.api.models.HashtagPreview

interface TrendsRepository {
    suspend fun getTopHashtags(): Result<List<HashtagPreview>>
}