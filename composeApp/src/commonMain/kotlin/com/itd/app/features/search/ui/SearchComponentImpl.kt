package com.itd.app.features.search.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.operator.map
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.decompose.asFlow
import com.itd.app.core.utils.SerializableTextFieldValue
import com.itd.app.core.utils.text
import com.itd.app.features.common.api.TrendsRepository
import com.itd.app.features.common.api.UsersRepository
import com.itd.app.features.search.api.SearchRepository
import com.itd.app.features.search.ui.mappers.toVO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

@OptIn(FlowPreview::class)
class SearchComponentImpl(
    componentContext: ComponentContext,
    private val trendsRepository: TrendsRepository,
    private val usersRepository: UsersRepository,
    private val searchRepository: SearchRepository,
    private val onOpenUser: (String) -> Unit,
    private val onOpenHashtag: (String) -> Unit,
) : SearchComponent,
    BaseComponent<SearchComponentState>(componentContext, SearchComponentState.serializer()) {

    init {
        componentScope.launch {
            state.map { it.searchText }.asFlow()
                .distinctUntilChanged()
                .debounce(SEARCH_DEBOUNCE)
                .collectLatest {
                    search(it.text)
                }
        }
    }

    override fun initialState() = SearchComponentState()

    override fun onTextChanged(text: SerializableTextFieldValue) {
        updateState { it.copy(searchText = text) }
    }

    override fun onOpenUser(username: String) {
        onOpenUser.invoke(username.drop(1))
    }

    override fun onOpenHashtag(hashtag: String) {
        onOpenHashtag.invoke(hashtag.drop(1))
    }

    private suspend fun search(searchText: String) {
        updateState { it.copy(isLoading = true) }

        if (searchText.isEmpty()) {
            updateTrends()
        } else {
            searchByText(searchText)
        }
    }

    private suspend fun updateTrends() {
        coroutineScope {
            val topTagsJob = async { trendsRepository.getTopHashtags() }
            val topUsersJob = async { usersRepository.getWhoToFollow() }
            val topClansJob = async { usersRepository.getTopClans() }

            topTagsJob.await().onSuccess { topTags ->
                val tagsVo = topTags
                    .mapIndexed { index, preview ->
                        preview.toVO(index)
                    }
                updateState {
                    it.copy(popularHashtags = tagsVo)
                }
            }

            topUsersJob.await().onSuccess { topUsers ->
                val usersVo = topUsers
                    .map { it.toVO() }
                updateState {
                    it.copy(popularUsers = usersVo)
                }
            }

            topClansJob.await().onSuccess { topClans ->
                val clansVo = topClans
                    .mapIndexed { index, preview ->
                        preview.toVO(index)
                    }
                updateState {
                    it.copy(topClans = clansVo)
                }
            }
            updateState { it.copy(isLoading = false) }
        }
    }

    private suspend fun searchByText(searchText: String) {
        searchRepository.search(searchText).onSuccess { searchResult ->
            updateState {
                it.copy(
                    isLoading = false,
                    foundUsers = searchResult.users.map { it.toVO() },
                    foundHashtags = searchResult.hashtags.mapIndexed { index, preview ->
                        preview.toVO(index)
                    }
                )
            }
        }.onFailure {
            updateState { it.copy(isLoading = false) }
        }
    }

    class Factory : SearchComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            onOpenUser: (String) -> Unit,
            onOpenHashtag: (String) -> Unit,
        ): SearchComponent {
            return getKoin().get { parametersOf(componentContext, onOpenUser, onOpenHashtag) }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 200L
    }
}