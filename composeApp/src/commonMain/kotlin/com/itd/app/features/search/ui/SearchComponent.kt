package com.itd.app.features.search.ui

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.core.utils.SerializableTextFieldValue

interface SearchComponent : Component<SearchComponentState> {
    fun onTextChanged(text: SerializableTextFieldValue)

    interface Factory {
        fun create(componentContext: ComponentContext): SearchComponent
    }
}