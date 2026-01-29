package com.itd.app.core.decompose

import kotlinx.serialization.Serializable

interface UIState

@Serializable
object EmptyState : UIState