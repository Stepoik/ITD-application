package com.itd.app.features.post.ui.fullpost.comments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.itd.app.core.utils.SerializableTextFieldValue
import com.itd.app.core.utils.px
import com.itd.app.core.utils.serializable
import com.itd.app.core.utils.text
import com.itd.app.features.common.ui.toAgoText
import com.itd.app.features.feed.ui.list.mappers.formatThousand
import com.itd.app.features.post.ui.components.MosaicAttachments
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.DefaultHorizontalDivider
import com.itd.app.uikit.components.VerticalSpacer
import com.itd.app.uikit.icons.ArrowRight
import com.itd.app.uikit.icons.Close
import com.itd.app.uikit.icons.Icons
import com.itd.app.uikit.icons.Like
import com.itd.app.uikit.icons.Menu

fun LazyListScope.comments(component: CommentsComponent, state: CommentsState) {
    state.comments.forEach { origComment ->
        item(key = origComment.id) {
            Column(Modifier.padding(horizontal = 16.dp)) {
                VerticalSpacer(12.dp)
                CommentItem(
                    origComment,
                    onAnswerClicked = {
                        component.onAnswerClicked(
                            origCommentId = origComment.id,
                            repliesTo = origComment.author.toRepliesTo()
                        )
                    })
                VerticalSpacer(12.dp)
            }
        }
        origComment.replies.forEach {
            item(key = it.id) {
                Column(Modifier.padding(horizontal = 16.dp).padding(start = 40.dp)) {
                    DefaultHorizontalDivider(thickness = 1.dp, Modifier.fillMaxWidth())
                    VerticalSpacer(12.dp)
                    CommentItem(it, onAnswerClicked = {
                        component.onAnswerClicked(
                            origCommentId = origComment.id,
                            repliesTo = it.author.toRepliesTo()
                        )
                    })
                    VerticalSpacer(12.dp)
                }
            }
        }
        if (origComment.showMore != null) {
            item {
                Column(Modifier.padding(start = 56.dp)) {
                    VerticalSpacer(12.dp)
                    ShowMoreView(
                        origComment.showMore,
                        onClick = { component.onShowMoreClicked(origComment.id) }
                    )
                    VerticalSpacer(12.dp)
                }
            }
        }
        if (origComment.answerField != null) {
            item {
                Column(Modifier.padding(horizontal = 16.dp).padding(start = 16.dp)) {
                    NewReply(
                        answerField = origComment.answerField,
                        onChange = {
                            component.onAnswerTextChanged(origCommentId = origComment.id, text = it)
                        },
                        onOpenUser = {
                            origComment.answerField.repliesToVO?.username?.let {
                                component.onOpenUser(it)
                            }
                        },
                        onClearReplyTo = {
                            component.onClearAnswerRepliesTo(origComment.id)
                        },
                        onSend = {
                            component.onSendAnswer(origComment.id)
                        }
                    )
                    VerticalSpacer(12.dp)
                }
            }
        }
        item {
            DefaultHorizontalDivider(
                thickness = 1.dp,
                Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun CommentItem(comment: CommentVO, onAnswerClicked: () -> Unit) {
    val commentText = buildAnnotatedString {
        if (comment.repliesTo != null) {
            val style = TextLinkStyles(
                style = SpanStyle(
                    color = ITDTheme.colors.primary,
                    fontWeight = FontWeight.Medium,
                    fontStyle = ITDTheme.typography.bodyUi.fontStyle
                )
            )
            val annotation = LinkAnnotation.Clickable(
                "ACTION", styles = style, linkInteractionListener = {
                    println("YO ${comment.repliesTo.username}")
                })
            withLink(annotation) {
                append("${comment.repliesTo.displayName}, ")
            }
        }
        append(comment.content)
    }
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                Text(comment.author.avatar, fontSize = 19.px)
            }
            Column {
                CommentHeader(comment, modifier = Modifier.fillMaxWidth())
                VerticalSpacer(4.dp)
                Text(
                    commentText,
                    style = ITDTheme.typography.bodyUi,
                    color = ITDTheme.colors.onBackground,
                )
                if (comment.attachments.isNotEmpty()) {
                    VerticalSpacer(8.dp)
                    MosaicAttachments(attachments = comment.attachments)
                }
                VerticalSpacer(4.dp)
                CommentActions(
                    isLiked = comment.isLiked,
                    likesCount = comment.likesCount.formatThousand(),
                    onAnswerClicked = onAnswerClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun CommentHeader(comment: CommentVO, modifier: Modifier = Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(
                comment.author.displayName,
                style = ITDTheme.typography.caption,
                color = ITDTheme.colors.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                comment.createdAt.toAgoText(),
                style = ITDTheme.typography.caption,
                color = ITDTheme.colors.secondary,
            )
        }
        Icon(Icons.Menu, modifier = Modifier.size(18.dp), contentDescription = null)
    }
}

@Composable
private fun CommentActions(
    isLiked: Boolean,
    likesCount: String,
    onAnswerClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (isLiked) {
        ITDTheme.colors.like
    } else {
        ITDTheme.colors.onBackground
    }
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Ответить",
            modifier = Modifier.clickable(onClick = onAnswerClicked).alpha(0.5f),
            style = ITDTheme.typography.small,
            color = ITDTheme.colors.onBackground,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = {})
        ) {
            Icon(
                Icons.Like,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp).alpha(0.5f)
            )
            Text(
                likesCount,
                style = ITDTheme.typography.small,
                color = color,
                modifier = Modifier.alpha(0.5f)
            )
        }
    }
}

@Composable
private fun ShowMoreView(
    showMoreVO: ShowMoreVO,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (showMoreVO) {
        is ShowMoreVO.Count -> {
            Text(
                "Показать ещё ${showMoreVO.commentsCount} ответов",
                style = ITDTheme.typography.small,
                color = ITDTheme.colors.primary,
                fontWeight = FontWeight.Medium,
                modifier = modifier.clickable(onClick = onClick)
            )
        }

        is ShowMoreVO.Loading -> {
            Text(
                "Загрузка...",
                style = ITDTheme.typography.small,
                color = ITDTheme.colors.primary,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun NewReply(
    answerField: AnswerField,
    onChange: (SerializableTextFieldValue) -> Unit,
    onClearReplyTo: () -> Unit,
    onOpenUser: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        if (answerField.repliesToVO != null) {
            val repliesToText = buildAnnotatedString {
                val style = TextLinkStyles(
                    style = SpanStyle(
                        color = ITDTheme.colors.primary,
                        fontWeight = FontWeight.Medium,
                        fontStyle = ITDTheme.typography.bodyUi.fontStyle
                    )
                )
                val annotation = LinkAnnotation.Clickable(
                    "ACTION",
                    styles = style,
                    linkInteractionListener = { onOpenUser(answerField.repliesToVO.username) })
                append("Ответ для ")
                withLink(link = annotation) {
                    append(answerField.repliesToVO.displayName)
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    repliesToText,
                    style = ITDTheme.typography.small,
                    color = ITDTheme.colors.onBackgroundInactive
                )
                Icon(
                    Icons.Close,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp).clickable(onClick = onClearReplyTo),
                    tint = ITDTheme.colors.onBackgroundInactive
                )
            }
        }
        NewComment(text = answerField.text, onChange = onChange, onSend = onSend)
    }
}

@Composable
fun NewComment(
    text: SerializableTextFieldValue,
    onChange: (SerializableTextFieldValue) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommentTextField(text = text, onChange = onChange, Modifier.weight(1f))
        SendButton(onClick = onSend)
    }
}

@Composable
private fun SendButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier.size(34.dp).clip(CircleShape)
            .background(ITDTheme.colors.primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.ArrowRight,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = ITDTheme.colors.onPrimary
        )
    }
}

@Composable
private fun CommentTextField(
    text: SerializableTextFieldValue,
    onChange: (SerializableTextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        text.textFieldValue,
        onValueChange = { onChange(it.serializable()) },
        modifier = modifier.onFocusChanged { isFocused = it.isFocused }
    ) { innerTextField ->
        val borderColor = if (isFocused) {
            ITDTheme.colors.primary
        } else ITDTheme.colors.border
        Box(
            Modifier.background(ITDTheme.colors.inputBg, shape = CircleShape)
                .border(
                    BorderStroke(width = 1.dp, color = borderColor),
                    shape = CircleShape
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            if (text.text.isEmpty()) {
                Text("Написать комментарий...")
            }
            innerTextField()
        }
    }
}