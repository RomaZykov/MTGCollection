package com.andreikslpv.common_impl

import com.andreikslpv.common.AppRestarter
import com.andreikslpv.common.CoreProvider
import com.andreikslpv.common.ErrorHandler
import com.andreikslpv.common.LoadStateHandler
import com.andreikslpv.common.Logger
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class DefaultCoreProvider(
    override val appRestarter: AppRestarter,
    override val logger: Logger = AndroidLogger(),
    override val loadStateHandler: LoadStateHandler = DefaultLoadStateHandler(),
    override val errorHandler: ErrorHandler = DefaultErrorHandler(
        logger, loadStateHandler
    ),
    override val globalScope: CoroutineScope = createDefaultGlobalScope(errorHandler),
    override val globalCoroutineContext: CoroutineContext = createDefaultGlobalCoroutineContext(
        errorHandler
    ),
) : CoreProvider