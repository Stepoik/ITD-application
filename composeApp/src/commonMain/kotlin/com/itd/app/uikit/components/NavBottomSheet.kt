package com.itd.app.uikit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.slot.ChildSlot
import com.composables.core.BottomSheet
import com.composables.core.BottomSheetState
import com.composables.core.SheetDetent
import com.composables.core.rememberBottomSheetState
import com.itd.app.uikit.ITDTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun <C : Any> NavBottomSheet(
    onHide: () -> Unit,
    childSlot: ChildSlot<Any, C>,
    content: @Composable (Child.Created<Any, C>?) -> Unit
) {
    var currentChildSlot by remember { mutableStateOf(childSlot) }
    val bottomSheetState = rememberNavBottomSheetState()
    LaunchedEffect(childSlot) {
        val showBottomSheet = childSlot.child?.key != null
        if (showBottomSheet
            && bottomSheetState.targetDetent != SheetDetent.ScreenExpanded) {
            currentChildSlot = childSlot
            bottomSheetState.animateTo(SheetDetent.ScreenExpanded)
        } else if (!showBottomSheet
            && bottomSheetState.targetDetent != SheetDetent.Hidden
        ) {
            bottomSheetState.animateTo(SheetDetent.Hidden)
            currentChildSlot = childSlot
        }
    }
    NavBottomSheet(onHide = onHide, state = bottomSheetState) {
        if (currentChildSlot.child != null) {
            content(currentChildSlot.child)
        } else {
            Box(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun NavBottomSheet(
    onHide: () -> Unit,
    state: BottomSheetState = rememberNavBottomSheetState(),
    content: @Composable () -> Unit
) {
    LaunchedEffect(state.currentDetent) {
        if (state.currentDetent == SheetDetent.Hidden && state.targetDetent == SheetDetent.Hidden) {
            onHide()
        }
    }
    BottomSheetContainer(state = state) {
        BottomSheet(
            state,
            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            backgroundColor = ITDTheme.colors.background,
        ) {
            content()
        }
    }
}

@Composable
fun rememberNavBottomSheetState(): BottomSheetState {
    return rememberBottomSheetState(
        initialDetent = SheetDetent.Hidden, detents = listOf(
            SheetDetent.Hidden, SheetDetent.ScreenExpanded
        )
    )
}

@Composable
private fun BottomSheetContainer(state: BottomSheetState, content: @Composable () -> Unit) {
    val target by remember(state) { derivedStateOf { state.targetDetent } }
    val current by remember(state) { derivedStateOf { state.currentDetent } }
    val baseColor = Color.Black
    val backgroundColor = if (target == SheetDetent.Hidden || current == SheetDetent.Hidden) {
        baseColor.copy(alpha = 0f)
    } else {
        baseColor.copy(alpha = 0.6f)
    }
    val backgroundColorState by animateColorAsState(
        backgroundColor,
        animationSpec = tween(easing = FastOutSlowInEasing)
    )
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        Modifier.drawBehind {
            drawRect(backgroundColorState, topLeft = Offset(0f, 0f), size = size)
        }.let {
            if (current != SheetDetent.Hidden) {
                it then Modifier.clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = interactionSource
                )
            } else it
        },
    ) {
        content()
    }
}

val SheetDetent.Companion.ScreenExpanded by lazy {
    SheetDetent(
        identifier = "screen_expanded",
        calculateDetentHeight = { containerHeight, sheetHeight ->
            containerHeight * 0.9f
        }
    )
}