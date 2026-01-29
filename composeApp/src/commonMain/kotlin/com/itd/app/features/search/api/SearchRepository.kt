package com.itd.app.features.search.api

import com.itd.app.features.search.api.models.SearchResult

interface SearchRepository {
    suspend fun search(text: String): Result<SearchResult>
}