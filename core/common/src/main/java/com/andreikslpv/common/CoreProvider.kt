package com.andreikslpv.common

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Provides a global entities for [Core] via [Core.init] method.
 */
interface CoreProvider {

    val logger: Logger

    val globalScope: CoroutineScope

    val globalCoroutineContext: CoroutineContext

    val errorHandler: ErrorHandler

    val appRestarter: AppRestarter

    val remoteTimeoutMillis: Long get() = 10_000L

    val localTimeoutMillis: Long get() = 3_000L

    val debouncePeriodMillis: Long get() = 200L

    val loadStateHandler: LoadStateHandler

}