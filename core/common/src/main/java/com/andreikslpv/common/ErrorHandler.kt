package com.andreikslpv.common

/**
 * Default global error handler.
 */
interface ErrorHandler {

    fun handleError(exception: Throwable)

}