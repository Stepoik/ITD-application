package com.itd.app.features.post.ui.new

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.core.utils.SerializableTextFieldValue

interface NewPostComponent : Component<NewPostState> {
    fun onUpdatePostContent(content: SerializableTextFieldValue)

    fun onSendPostClicked()

    fun onCloseClicked()

    interface Factory {
        fun create(componentContext: ComponentContext, onClose: () -> Unit): NewPostComponent
    }
}