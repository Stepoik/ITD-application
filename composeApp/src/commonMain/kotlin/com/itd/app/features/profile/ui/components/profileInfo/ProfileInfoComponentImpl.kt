package com.itd.app.features.profile.ui.components.profileInfo

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.profile.api.ProfileRepository
import com.itd.app.features.profile.ui.components.profileInfo.mappers.toInfo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class ProfileInfoComponentImpl(
    componentContext: ComponentContext,
    private val profileRepository: ProfileRepository,
    private val profileUsername: String,
) : ProfileInfoComponent,
    BaseComponent<ProfileInfoState>(componentContext, ProfileInfoState.serializer()) {
    init {
        componentScope.launch {
            profileRepository.getProfile(profileUsername).onSuccess { profile ->
                updateState { it.copy(info = profile.toInfo()) }
            }
        }
    }

    override fun initialState() = ProfileInfoState()

    class Factory : ProfileInfoComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            username: String
        ): ProfileInfoComponent {
            return getKoin().get { parametersOf(componentContext, username) }
        }
    }
}