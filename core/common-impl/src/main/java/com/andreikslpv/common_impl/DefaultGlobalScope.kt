package com.andreikslpv.common_impl

import com.andreikslpv.common.ErrorHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

fun createDefaultGlobalScope(errorHandler: ErrorHandler): CoroutineScope {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        errorHandler.handleError(exception)
    }
    return CoroutineScope(SupervisorJob() + Dispatchers.Main + coroutineExceptionHandler)
}

fun createDefaultGlobalCoroutineContext(errorHandler: ErrorHandler): CoroutineContext {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        errorHandler.handleError(exception)
    }
    return SupervisorJob() + coroutineExceptionHandler
}