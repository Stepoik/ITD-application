package com.itd.app.features.post.ui.new

import com.itd.app.core.decompose.UIState
import com.itd.app.core.utils.SerializableTextFieldValue
import kotlinx.serialization.Serializable

@Serializable
data class NewPostState(
    val content: SerializableTextFieldValue = SerializableTextFieldValue.EMPTY,
    val avatar: String = "",
    val isSending: Boolean = false
): UIState
