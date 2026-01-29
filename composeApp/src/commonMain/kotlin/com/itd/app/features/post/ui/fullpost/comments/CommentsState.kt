package com.itd.app.features.post.ui.fullpost.comments

import com.itd.app.core.decompose.UIState
import com.itd.app.core.utils.SerializableTextFieldValue
import com.itd.app.features.post.ui.components.AttachmentVO
import com.itd.app.features.post.ui.components.AuthorVO
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class CommentsState(
    val isLoading: Boolean = false,
    val comments: List<CommentVO> = listOf(),
    val newCommentText: SerializableTextFieldValue = SerializableTextFieldValue.EMPTY
) : UIState

@Serializable
data class CommentVO(
    val repliesTo: RepliesToVO?,
    val repliesCount: Int,
    val attachments: List<AttachmentVO>,
    val author: AuthorVO,
    val content: String,
    val createdAt: LocalDateTime,
    val id: String,
    val isLiked: Boolean,
    val likesCount: Int,
    val replies: List<CommentVO>,
    val showMore: ShowMoreVO?,
    val answerField: AnswerField?
)

@Serializable
data class RepliesToVO(
    val username: String,
    val userId: String,
    val displayName: String
)

@Serializable
sealed class ShowMoreVO {
    @Serializable
    data class Count(val commentsCount: Int) : ShowMoreVO()

    @Serializable
    object Loading : ShowMoreVO()
}

@Serializable
data class AnswerField(
    val repliesToVO: RepliesToVO?,
    val text: SerializableTextFieldValue
)