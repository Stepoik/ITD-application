package com.itd.app.features.search.ui

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.core.utils.SerializableTextFieldValue

interface SearchComponent : Component<SearchComponentState> {
    fun onTextChanged(text: SerializableTextFieldValue)

    fun onOpenUser(username: String)

    fun onOpenHashtag(hashtag: String)

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onOpenUser: (String) -> Unit,
            onOpenHashtag: (String) -> Unit,
        ): SearchComponent
    }
}