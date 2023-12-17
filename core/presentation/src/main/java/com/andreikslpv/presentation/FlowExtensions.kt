package com.andreikslpv.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

/**
 * Listen the [Flow] only in [Lifecycle.State.STARTED] state of the lifecycle.
 */
fun <T> Flow<T>.observeStateOn(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest {
                block(it)
            }
        }
    }
}

// CustomFlowOperator
/* Emits the previous values ('null' if there is no previous values) along with the current one.
For example:
- original flow:
"a", "b", "c" ...
- resulting flow (count = 2):
(null, null), (null, "a"), ("a", "b"), ("b", "c"), ...
 */
fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value -> previous.drop(1) + value }
}