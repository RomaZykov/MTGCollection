package com.andreikslpv.wiring

import android.content.Context
import com.andreikslpv.common.*
import com.andreikslpv.common_impl.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import kotlinx.coroutines.CoroutineScope


@Module
@InstallIn(SingletonComponent::class)
class CoreProviderModule {

    @Provides
    fun provideCoreProvider(
        @ApplicationContext context: Context,
        appRestarter: AppRestarter,
    ): CoreProvider {
        return DefaultCoreProvider(
            appContext = context,
            appRestarter = appRestarter
        )
    }

    @Provides
    @ElementsIntoSet
    fun provideActivityRequiredSet(
        commonUi: CommonUi,
//        screenCommunication: ScreenCommunication,
    ): Set<@JvmSuppressWildcards ActivityRequired> {
        val set = hashSetOf<ActivityRequired>()
        if (commonUi is ActivityRequired) set.add(commonUi)
        //if (screenCommunication is ActivityRequired) set.add(screenCommunication)
        return set
    }

//    @Provides
//    fun provideScreenCommunication(): ScreenCommunication {
//        return Core.screenCommunication
//    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return Core.globalScope
    }

    @Provides
    fun provideCommonUi(): CommonUi {
        return Core.commonUi
    }

    @Provides
    fun provideLogger(): Logger {
        return Core.logger
    }

    @Provides
    fun provideResources(): Resources {
        return Core.resources
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