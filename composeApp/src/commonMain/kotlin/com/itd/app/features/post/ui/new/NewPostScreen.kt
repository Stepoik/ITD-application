package com.itd.app.features.post.ui.new

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.composables.core.Icon
import com.composeunstyled.Text
import com.itd.app.core.utils.serializable
import com.itd.app.core.utils.text
import com.itd.app.uikit.ITDTheme
import com.itd.app.uikit.components.BaseScaffold
import com.itd.app.uikit.components.DefaultHorizontalDivider
import com.itd.app.uikit.components.HorizontalSpacer
import com.itd.app.uikit.components.ITDButton
import com.itd.app.uikit.icons.Close
import com.itd.app.uikit.icons.Icons

@Composable
fun NewPostScreen(component: NewPostComponent) {
    val state = component.state.subscribeAsState().value
    BaseScaffold {
        Column(Modifier.padding(it)) {
            NewPostHeader(
                onCloseClicked = component::onCloseClicked,
                onSendClicked = component::onSendPostClicked
            )
            DefaultHorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())
            Row(Modifier.padding(16.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Text(state.avatar, fontSize = 24.sp)
                }
                HorizontalSpacer(16.dp)
                BasicTextField(
                    state.content.textFieldValue,
                    onValueChange = { component.onUpdatePostContent(it.serializable()) },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = ITDTheme.typography.titleSmall.copy(color = ITDTheme.colors.onBackground),
                ) { innerTextField ->
                    Box {
                        if (state.content.text.isEmpty()) {
                            Text(
                                "Что нового",
                                style = ITDTheme.typography.titleSmall.copy(color = ITDTheme.colors.onBackground),
                                modifier = Modifier.alpha(0.5f)
                            )
                        }
                        innerTextField()
                    }
                }
            }
        }
    }
}

@Composable
private fun NewPostHeader(onCloseClicked: () -> Unit, onSendClicked: () -> Unit) {
    Row(
        Modifier.padding(vertical = 12.dp, horizontal = 16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Close,
            tint = ITDTheme.colors.onBackground,
            contentDescription = null,
            modifier = Modifier.size(24.dp).clickable(onClick = onCloseClicked)
        )
        ITDButton(text = "Опубликовать", onClick = onSendClicked)
    }
}