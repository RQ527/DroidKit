package com.rsix.library.extension

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

fun <T> StateFlow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    handle: (T) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
        .collect { handle(it) }
}

fun <T, R> StateFlow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    prop: KProperty1<T, R>,
    action: (R) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).map { prop.get(it) }
        .collect { action(it) }
}

fun <T> SharedFlow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    handle: (T) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
        .onEach { handle(it) }.collect()
}

fun <T, R> SharedFlow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    prop: KProperty1<T, R>,
    action: (R) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).map { prop.get(it) }
        .collect { action(it) }
}

fun <T> SharedFlow<T>.collectUntilChange(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    handle: (T) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).distinctUntilChanged()
        .collect { handle(it) }
}

fun <T, R> SharedFlow<T>.collectUntilChange(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    prop: KProperty1<T, R>,
    action: (R) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).map { prop.get(it) }
        .distinctUntilChanged().collect { action(it) }
}

@Composable
fun <T> StateFlow<T>.collectAsStateWittLifecycle() = collectAsStateWithLifecycle(value)
@Composable
fun <T, R> StateFlow<T>.collectAsStateWittLifecycle(
    prop: KProperty1<T, R>,
) = map { prop.get(it) }.collectAsStateWithLifecycle(value)

@Composable
fun <T, R> SharedFlow<T>.collectAsStateWittLifecycle(
    prop: KProperty1<T, R>,
    initialValue: R
) = map { prop.get(it) }.collectAsStateWithLifecycle(initialValue)

@Composable
fun <T> SharedFlow<T>.collectDistinctAsState(
    initialValue:T
) = distinctUntilChanged().collectAsStateWithLifecycle(initialValue)

@Composable
fun <T, R> SharedFlow<T>.collectDistinctAsState(
    prop: KProperty1<T, R>,
    initialValue: R
) = map { prop.get(it) }.distinctUntilChanged().collectAsStateWithLifecycle(initialValue)