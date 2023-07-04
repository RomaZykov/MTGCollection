package com.andreikslpv.navigation.di

import com.andreikslpv.common.AppRestarter
import com.andreikslpv.common_impl.ActivityRequired
import com.andreikslpv.navigation.GlobalNavComponentRouter
import com.andreikslpv.navigation.MainAppRestarter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Provides
    fun provideAppRestarter(
        appRestarter: MainAppRestarter
    ): AppRestarter {
        return appRestarter
    }

    @Provides
    @IntoSet
    fun provideRouterAsActivityRequired(
        router: GlobalNavComponentRouter,
    ): ActivityRequired {
        return router
    }


}