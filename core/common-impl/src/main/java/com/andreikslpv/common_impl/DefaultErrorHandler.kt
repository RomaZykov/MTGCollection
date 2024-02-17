package com.andreikslpv.common_impl

import com.andreikslpv.common.ErrorHandler
import com.andreikslpv.common.LoadStateHandler
import com.andreikslpv.common.Logger
import com.andreikslpv.common.Response

class DefaultErrorHandler(
    private val logger: Logger,
    private val loadStateHandler: LoadStateHandler,
) : ErrorHandler {

    private var lastRestartTimestamp = 0L

    override fun handleError(exception: Throwable) {
        logger.err(exception)
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastRestartTimestamp > REPEAT_TIMEOUT) {
            lastRestartTimestamp = currentTimestamp
            loadStateHandler.setLoadState(Response.Failure(exception))
        }
    }

    private companion object {
        const val REPEAT_TIMEOUT = 2000L
    }
}