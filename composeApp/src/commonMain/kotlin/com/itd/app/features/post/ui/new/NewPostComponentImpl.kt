package com.itd.app.features.post.ui.new

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.utils.SerializableTextFieldValue
import com.itd.app.core.utils.text
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.profile.api.ProfileRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class NewPostComponentImpl(
    componentContext: ComponentContext,
    private val profileRepository: ProfileRepository,
    private val postsRepository: PostsRepository,
    private val onClose: () -> Unit
) : NewPostComponent, BaseComponent<NewPostState>(componentContext, NewPostState.serializer()) {
    init {
        componentScope.launch {
            profileRepository.getMe().onSuccess { profile ->
                updateState { it.copy(avatar = profile.avatar) }
            }
        }
    }

    override fun initialState() = NewPostState()

    override fun onUpdatePostContent(content: SerializableTextFieldValue) {
        updateState { it.copy(content = content) }
    }

    override fun onSendPostClicked() {
        val state = state.value
        if (state.isSending) return

        val content = state.content.text
        updateState { it.copy(isSending = true) }
        componentScope.launch {
            postsRepository.createNewPost(content).onSuccess {
                onClose.invoke()
            }.onFailure {
                updateState { it.copy(isSending = false) }
            }
        }
    }

    override fun onCloseClicked() {
        onClose.invoke()
    }

    class Factory : NewPostComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext, onClose: () -> Unit): NewPostComponent {
            return getKoin().get { parametersOf(componentContext, onClose) }
        }
    }
}