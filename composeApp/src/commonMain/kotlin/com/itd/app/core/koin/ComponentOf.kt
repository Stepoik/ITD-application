package com.itd.app.core.koin

import com.itd.app.core.decompose.Component
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.DefinitionOptions
import org.koin.core.module.dsl.factoryOf

// 0 параметров
inline fun <reified C : Component<*>> Module.componentOf(
    crossinline constructor: () -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 1 параметр
inline fun <reified C : Component<*>, reified T1> Module.componentOf(
    crossinline constructor: (T1) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 2 параметра
inline fun <reified C : Component<*>, reified T1, reified T2> Module.componentOf(
    crossinline constructor: (T1, T2) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 3 параметра
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3> Module.componentOf(
    crossinline constructor: (T1, T2, T3) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 4 параметра
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 5 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 6 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 7 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 8 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 9 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 10 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 11 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 12 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 13 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 14 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 15 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

// 16 параметров
inline fun <reified C : Component<*>, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16> Module.componentOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> C,
    noinline options: DefinitionOptions<C>? = null
): KoinDefinition<C> {
    return factoryOf(constructor, options = options)
}

