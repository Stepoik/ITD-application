package com.itd.app.features.profile.ui.components.profileInfo

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component

interface ProfileInfoComponent : Component<ProfileInfoState> {
    interface Factory {
        fun create(componentContext: ComponentContext, username: String): ProfileInfoComponent
    }
}