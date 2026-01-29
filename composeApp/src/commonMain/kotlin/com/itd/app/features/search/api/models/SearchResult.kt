package com.itd.app.features.search.api.models

import com.itd.app.features.common.api.models.HashtagPreview
import com.itd.app.features.common.api.models.UserPreview

data class SearchResult(
    val hashtags: List<HashtagPreview>,
    val users: List<UserPreview>
)
