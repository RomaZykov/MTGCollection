package com.andreikslpv.common_impl

import android.content.Context
import com.andreikslpv.common.AppRestarter
import com.andreikslpv.common.CommonUi
import com.andreikslpv.common.CoreProvider
import com.andreikslpv.common.ErrorHandler
import com.andreikslpv.common.LoadStateHandler
import com.andreikslpv.common.Logger
import com.andreikslpv.common.Resources
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class DefaultCoreProvider(
    private val appContext: Context,
    override val appRestarter: AppRestarter,
    override val commonUi: CommonUi = AndroidCommonUi(appContext),
    override val resources: Resources = AndroidResources(appContext),
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