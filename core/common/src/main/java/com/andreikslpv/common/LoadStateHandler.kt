package com.andreikslpv.common

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Default global load state handler for actions.
 */

interface LoadStateHandler {

    fun setLoadState(response: Response<Any>)

    fun getLoadState(): MutableStateFlow<Response<Any>>

}