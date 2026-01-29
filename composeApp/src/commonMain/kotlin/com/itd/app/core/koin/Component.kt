package com.itd.app.core.koin

import com.arkivanov.decompose.ComponentContext
import com.itd.app.core.decompose.Component
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.ScopeDSL

typealias ComponentDefinition<T> = Scope.(ComponentContext, ParametersHolder) -> T
typealias SimpleComponentDefinition<T> = Scope.(ComponentContext) -> T

inline fun <reified T : Component<*>> Module.component(
    qualifier: Qualifier? = null,
    noinline definition: ComponentDefinition<T>,
): KoinDefinition<T> {
    val koinDefinition: Definition<T> = { params -> definition(params.get(), params) }
    return factory(qualifier, koinDefinition)
}

inline fun <reified T : Component<*>> Module.component(
    qualifier: Qualifier? = null,
    noinline definition: SimpleComponentDefinition<T>,
): KoinDefinition<T> {
    val koinDefinition: Definition<T> = { params -> definition(params.get()) }
    return factory(qualifier, koinDefinition)
}

inline fun <reified T : Component<*>> ScopeDSL.component(
    qualifier: Qualifier? = null,
    noinline definition: ComponentDefinition<T>,
): KoinDefinition<T> {
    val koinDefinition: Definition<T> = { params -> definition(params.get(), params) }
    return factory(qualifier, koinDefinition)
}

inline fun <reified T : Component<*>> ScopeDSL.component(
    qualifier: Qualifier? = null,
    noinline definition: SimpleComponentDefinition<T>,
): KoinDefinition<T> {
    val koinDefinition: Definition<T> = { params -> definition(params.get()) }
    return factory(qualifier, koinDefinition)
}

inline fun <reified T> Module.componentFactory(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
): KoinDefinition<T> {
    return factory(qualifier, definition)
}

inline fun <reified T> ScopeDSL.componentFactory(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
): KoinDefinition<T> {
    return factory(qualifier, definition)
}