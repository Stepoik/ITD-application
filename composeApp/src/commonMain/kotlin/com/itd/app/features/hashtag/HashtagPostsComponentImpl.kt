package com.itd.app.features.hashtag

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.ui.list.mappers.toVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class HashtagPostsComponentImpl(
    componentContext: ComponentContext,
    private val hashtag: String,
    private val onOpenPost: (String) -> Unit,
    private val onRepost: (String) -> Unit,
    private val onOpenUser: (String) -> Unit,
    private val onOpenHashtag: (String) -> Unit,
    private val onBack: () -> Unit,
    private val postsRepository: PostsRepository
) : HashtagPostsComponent,
    BaseComponent<HashtagPostsState>(componentContext, HashtagPostsState.serializer()) {
    init {
        updateState {
            it.copy(hashtagInfo = HashtagInfo(hashtag = "#$hashtag"))
        }
        onLoadNext()
    }


    override fun initialState() = HashtagPostsState()
    override fun onOpenPost(postId: String) {
        onOpenPost.invoke(postId)
    }

    override fun onLike(postId: String) {
        TODO("LIKE")
        componentScope.launch {
//            postsRepository.likePost(postId)
        }
    }

    override fun onRepost(postId: String) {
        onRepost.invoke(postId)
    }

    override fun onComment(postId: String) {
        onOpenUser.invoke(postId)
    }

    override fun onOpenUser(username: String) {
        onOpenUser.invoke(username.drop(1))
    }

    override fun onOpenHashtag(hashtag: String) {
        onOpenHashtag.invoke(hashtag)
    }

    override fun onNavigateBackClicked() {
        onBack.invoke()
    }

    override fun onLoadNext() {
        val state = state.value
        if (state.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch(Dispatchers.Default) {
            postsRepository.getPostsByHashtag(hashtag, lastPostId = state.posts.lastOrNull()?.id)
                .onSuccess { newPosts ->
                    updateState {
                        it.copy(
                            posts = it.posts + newPosts.posts.map { it.toVO() },
                            hashtagInfo = it.hashtagInfo.copy(postsCount = "${newPosts.postsCount} постов")
                        )
                    }
                }.onFailure {
                    updateState { it.copy(isLoading = false) }
                }
        }
    }

    class Factory : HashtagPostsComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            hashtag: String,
            onOpenPost: (String) -> Unit,
            onRepost: (String) -> Unit,
            onOpenUser: (String) -> Unit,
            onOpenHashtag: (String) -> Unit,
            onBack: () -> Unit
        ): HashtagPostsComponent {
            return getKoin().get {
                parametersOf(
                    componentContext,
                    hashtag,
                    onOpenPost,
                    onRepost,
                    onOpenUser,
                    onOpenHashtag,
                    onBack
                )
            }
        }
    }
}