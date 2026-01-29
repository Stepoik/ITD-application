package com.itd.app.features.home

import com.itd.app.core.decompose.UIState
import kotlinx.serialization.Serializable

@Serializable
data class HomeState(
    val profileAvatar: String,
    val profileUsername: String
): UIState
