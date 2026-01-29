package com.itd.app.features.post.ui.fullpost.comments

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import com.itd.app.core.utils.SerializableTextFieldValue

interface CommentsComponent : Component<CommentsState> {
    fun onLoadNext()

    fun onAnswerClicked(origCommentId: String, repliesTo: RepliesToVO)

    fun onAnswerTextChanged(origCommentId: String, text: SerializableTextFieldValue)

    fun onOpenUser(username: String)

    fun onClearAnswerRepliesTo(origCommentId: String)

    fun onShowMoreClicked(origCommentId: String)

    fun onSendAnswer(origCommentId: String)

    fun onNewCommentChanged(text: SerializableTextFieldValue)

    fun onSendCommentClicked()

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            postId: String,
            onOpenUser: (String) -> Unit
        ): CommentsComponent
    }
}