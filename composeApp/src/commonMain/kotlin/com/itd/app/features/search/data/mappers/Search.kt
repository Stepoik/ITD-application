package com.itd.app.features.search.data.mappers

import com.itd.app.features.common.data.mappers.toDomain
import com.itd.app.features.search.api.models.SearchResult
import com.itd.app.features.search.data.dto.SearchResponse

fun SearchResponse.toDomain(): SearchResult {
    return SearchResult(
        hashtags = data.hashtags.map { it.toDomain() },
        users = data.users.map { it.toDomain() },
    )
}