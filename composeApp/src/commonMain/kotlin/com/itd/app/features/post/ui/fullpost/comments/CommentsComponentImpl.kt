package com.itd.app.features.post.ui.fullpost.comments

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.BaseComponent
import com.itd.app.core.utils.SerializableTextFieldValue
import com.itd.app.core.utils.text
import com.itd.app.features.post.api.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class CommentsComponentImpl(
    componentContext: ComponentContext,
    private val commentsRepository: CommentsRepository,
    private val postId: String,
    private val onOpenUser: (String) -> Unit
) : CommentsComponent, BaseComponent<CommentsState>(componentContext, CommentsState.serializer()) {
    init {
        onLoadNext()
    }

    override fun initialState() = CommentsState()

    override fun onLoadNext() {
        val state = state.value
        if (state.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch(Dispatchers.Default) {
            commentsRepository.getComments(postId, offset = state.comments.size)
                .onSuccess { comments ->
                    updateState {
                        val comments = it.comments + comments.map { it.toVO() }
                        it.copy(
                            isLoading = false,
                            comments = comments
                        )
                    }
                }.onFailure {
                    updateState { it.copy(isLoading = false) }
                }
        }
    }

    override fun onAnswerClicked(
        origCommentId: String,
        repliesTo: RepliesToVO
    ) {
        componentScope.launch(Dispatchers.Default) {
            updateState {
                val comments = it.comments.map {
                    if (it.id == origCommentId) {
                        it.copy(
                            answerField = AnswerField(
                                repliesTo,
                                SerializableTextFieldValue.EMPTY
                            )
                        )
                    } else it
                }
                it.copy(comments = comments)
            }
        }
    }

    override fun onAnswerTextChanged(
        origCommentId: String,
        text: SerializableTextFieldValue
    ) {
        componentScope.launch(Dispatchers.Default) {
            updateState {
                val comments = it.comments.map {
                    if (it.id == origCommentId) {
                        it.copy(answerField = it.answerField?.copy(text = text))
                    } else it
                }
                it.copy(comments = comments)
            }
        }
    }

    override fun onOpenUser(username: String) {
        onOpenUser.invoke(username)
    }

    override fun onClearAnswerRepliesTo(origCommentId: String) {
        componentScope.launch(Dispatchers.Default) {
            updateState {
                val comments = it.comments.map {
                    if (it.id == origCommentId) {
                        it.copy(answerField = it.answerField?.copy(repliesToVO = null))
                    } else it
                }
                it.copy(comments = comments)
            }
        }
    }

    override fun onShowMoreClicked(origCommentId: String) {
        componentScope.launch(Dispatchers.Default) {
            val origComment =
                state.value.comments.firstOrNull { it.id == origCommentId } ?: return@launch
            if (origComment.showMore is ShowMoreVO.Loading) return@launch
            updateComment(origCommentId) {
                it.copy(showMore = ShowMoreVO.Loading)
            }
            commentsRepository.getReplies(
                commentId = origCommentId,
                offset = origComment.replies.size
            ).onSuccess { newReplies ->
                updateComment(origCommentId) {
                    val newReplies =
                        LinkedHashSet(it.replies + newReplies.map { it.toVO() }).toList()
                    val showMore = if (it.repliesCount > newReplies.size) {
                        ShowMoreVO.Count(it.repliesCount - newReplies.size)
                    } else null
                    it.copy(showMore = showMore, replies = newReplies)
                }
            }.onFailure {
                updateComment(origCommentId) {
                    it.copy(showMore = origComment.showMore)
                }
            }
        }
    }

    override fun onSendAnswer(origCommentId: String) {
        componentScope.launch(Dispatchers.Default) {
            val comment =
                state.value.comments.firstOrNull { it.id == origCommentId } ?: return@launch
            val content = comment.answerField?.text?.text ?: return@launch
            val userId = comment.answerField.repliesToVO?.userId ?: return@launch

            updateComment(origCommentId) {
                it.copy(answerField = null)
            }
            commentsRepository.createReply(
                commentId = comment.id,
                content = content,
                userId = userId
            ).onSuccess { newReply ->
                updateComment(origCommentId) {
                    it.copy(replies = it.replies + newReply.toVO())
                }
            }
        }
    }

    override fun onNewCommentChanged(text: SerializableTextFieldValue) {
        updateState {
            it.copy(newCommentText = text)
        }
    }

    override fun onSendCommentClicked() {
        val commentText = state.value.newCommentText.text
        if (commentText.isEmpty()) return

        componentScope.launch {
            updateState {
                it.copy(newCommentText = SerializableTextFieldValue.EMPTY)
            }
            commentsRepository.createComment(postId = postId, content = commentText)
                .onSuccess { newComment ->
                    updateState {
                        it.copy(comments = it.comments + newComment.toVO())
                    }
                }
        }
    }

    private fun updateComment(commentId: String, block: (CommentVO) -> CommentVO) {
        updateState {
            val comments = it.comments.map {
                if (it.id == commentId) {
                    block(it)
                } else it
            }
            it.copy(comments = comments)
        }
    }

    class Factory : CommentsComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            postId: String,
            onOpenUser: (String) -> Unit
        ): CommentsComponent {
            return getKoin().get { parametersOf(componentContext, postId, onOpenUser) }
        }
    }
}