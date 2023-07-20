package com.andreikslpv.presentation.views

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.andreikslpv.common.Response
import com.andreikslpv.presentation.observeStateOn
import kotlinx.coroutines.flow.Flow

/**
 * Observe the specified [liveData] and call [onSuccess] callback
 * if the [liveData] contains [Response.Success] value.
 */
fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveData: LiveData<Response<T>>,
    onSuccess: (T) -> Unit
) {
    liveData.observe(owner) {
        response = it
        if (it is Response.Success) {
            onSuccess(it.data)
        }
    }
}

/**
 * Observe the specified [flow] and call [onSuccess] callback
 * if the [flow] contains [Response.Success] value.
 */
fun <T> ResultView.observe(
    owner: LifecycleOwner,
    flow: Flow<Response<T>>,
    onSuccess: (T) -> Unit
) {
    flow.observeStateOn(owner) {
        response = it
        if (it is Response.Success) {
            onSuccess(it.data)
        }
    }
}