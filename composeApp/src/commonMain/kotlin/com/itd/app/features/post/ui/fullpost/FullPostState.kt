package com.itd.app.features.post.ui.fullpost

import com.itd.app.core.decompose.UIState
import kotlinx.serialization.Serializable

@Serializable
data class FullPostState(
    val isLoading: Boolean = false
): UIState
