package com.itd.app.features.search.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.itd.app.core.decompose.UIState
import com.itd.app.core.utils.SerializableTextFieldValue
import com.itd.app.core.utils.text
import kotlinx.serialization.Serializable

sealed class SearchListState {
    class Loading : SearchListState()

    data class EmptySearch(
        val popularHashtags: List<HashtagVO>,
        val popularUsers: List<UserVO>,
        val topClans: List<ClanVO>
    ) : SearchListState()

    data class SearchFound(
        val foundUsers: List<UserVO>,
        val foundHashtags: List<HashtagVO>
    ) : SearchListState()
}

@Serializable
data class SearchComponentState(
    val isLoading: Boolean = false,
    val searchText: SerializableTextFieldValue = SerializableTextFieldValue.EMPTY,
    val popularHashtags: List<HashtagVO> = listOf(),
    val popularUsers: List<UserVO> = listOf(),
    val topClans: List<ClanVO> = listOf(),
    val foundUsers: List<UserVO> = listOf(),
    val foundHashtags: List<HashtagVO> = listOf()
) : UIState {
    fun toListState(): SearchListState {
        return when {
            isLoading -> SearchListState.Loading()
            searchText.text.isEmpty() -> SearchListState.EmptySearch(
                popularHashtags = popularHashtags,
                popularUsers = popularUsers,
                topClans = topClans
            )

            else -> SearchListState.SearchFound(
                foundUsers = foundUsers,
                foundHashtags = foundHashtags
            )
        }
    }
}

@Serializable
data class HashtagVO(
    val id: String,
    val index: String,
    val name: String,
    val postsCount: String
)

@Serializable
data class UserVO(
    val id: String,
    val displayName: String,
    val username: String,
    val followersCount: String,
    val avatar: String,
    val verified: Boolean
)

@Serializable
data class ClanVO(
    val index: String,
    val avatar: String,
    val memberCount: String,
    val highlighted: Boolean
)