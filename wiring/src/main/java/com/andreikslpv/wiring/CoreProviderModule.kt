package com.andreikslpv.wiring

import com.andreikslpv.common.*
import com.andreikslpv.common_impl.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)
class CoreProviderModule {

    @Provides
    fun provideCoreProvider(appRestarter: AppRestarter): CoreProvider {
        return DefaultCoreProvider(appRestarter = appRestarter)
    }

    @Provides
    @ElementsIntoSet
    fun provideActivityRequiredSet(
//        commonUi: CommonUi,
//        screenCommunication: ScreenCommunication,
    ): Set<@JvmSuppressWildcards ActivityRequired> {
        val set = hashSetOf<ActivityRequired>()
        //if (commonUi is ActivityRequired) set.add(commonUi)
        //if (screenCommunication is ActivityRequired) set.add(screenCommunication)
        return set
    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return Core.globalScope
    }

    @Provides
    fun provideCoroutineContext(): CoroutineContext {
        return Core.globalCoroutineContext
    }

    @Provides
    fun provideLogger(): Logger {
        return Core.logger
    }

    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return Core.errorHandler
    }

    @Provides
    fun provideLoadStateHandler(): LoadStateHandler {
        return Core.loadStateHandler
    }

}