package com.itd.app.core.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.value.Value

@Stable
interface Component<S : UIState> {
    val state: Value<S>
}