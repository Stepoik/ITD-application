package com.itd.app.features.profile.ui

import com.itd.app.core.decompose.UIState
import kotlinx.serialization.Serializable

@Serializable
data class ProfileState(
    val isLoading: Boolean = false
): UIState