package com.itd.app.core.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseComponent<S : UIState>(
    private val componentContext: ComponentContext,
    private val serializer: KSerializer<S>
) : Component<S>, ComponentContext by componentContext {
    val componentScope = getComponentScope()

    private val _state by lazy { MutableValue(initialState()) }
    override val state: Value<S> get() = _state

    init {
        doOnDestroy {
            componentScope.cancel()
        }

        stateKeeper.consume(STATE_SAVER, serializer)?.let { oldState ->
            updateState { oldState }
        }

        stateKeeper.register(STATE_SAVER, serializer) {
            saveState(_state.value)
        }
    }

    abstract fun initialState(): S

    protected fun updateState(function: (S) -> S) {
        _state.update(function)
    }

    open fun saveState(state: S): S {
        return state
    }

    companion object {
        private const val STATE_SAVER = "state"
    }
}

private fun getComponentScope(): CoroutineScope {
    val dispatcher =
        try {
            // In platforms where `Dispatchers.Main` is not available, Kotlin Multiplatform will
            // throw
            // an exception (the specific exception type may depend on the platform). Since there's
            // no
            // direct functional alternative, we use `EmptyCoroutineContext` to ensure that a
            // coroutine
            // launched within this scope will run in the same context as the caller.
            Dispatchers.Main.immediate
        } catch (_: NotImplementedError) {
            // In Native environments where `Dispatchers.Main` might not exist (e.g., Linux):
            EmptyCoroutineContext
        } catch (_: IllegalStateException) {
            // In JVM Desktop environments where `Dispatchers.Main` might not exist (e.g., Swing):
            EmptyCoroutineContext
        }
    return CoroutineScope(dispatcher + SupervisorJob())
}

fun <T> BaseComponent<*>.collectFlow(
    flow: Flow<T>,
    context: CoroutineContext = EmptyCoroutineContext,
    collector: FlowCollector<T>
) {
    componentScope.launch(context) {
        flow.collect(collector)
    }
}