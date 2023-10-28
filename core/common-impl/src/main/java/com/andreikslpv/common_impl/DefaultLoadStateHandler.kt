package com.andreikslpv.common_impl

import com.andreikslpv.common.LoadStateHandler
import com.andreikslpv.common.Response
import kotlinx.coroutines.flow.MutableStateFlow

class DefaultLoadStateHandler: LoadStateHandler {

    private val loadState = MutableStateFlow<Response<Any?>>(Response.Success(true))

    override fun setLoadState(response: Response<Any?>) {
        loadState.tryEmit(response)
    }

    override fun getLoadState(): MutableStateFlow<Response<Any?>> {
        return loadState
    }

}