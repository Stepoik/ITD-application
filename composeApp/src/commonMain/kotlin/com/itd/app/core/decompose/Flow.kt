package com.itd.app.core.decompose

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T : Any> Value<T>.asFlow(): Flow<T> {
    return callbackFlow {
        val subscription = subscribe {
            trySend(it)
        }

        awaitClose {
            subscription.cancel()
        }
    }
}