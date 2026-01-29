package com.itd.app.core.decompose

import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackHandler

fun BackHandler.register(onBack: () -> Unit) {
    register(object : BackCallback() {
        override fun onBack() = onBack()
    })
}