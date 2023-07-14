package com.andreikslpv.mtgcollection.glue.navigation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import com.andreikslpv.mtgcollection.glue.navigation.DefaultMainRouter
import com.andreikslpv.navigation.presentation.MainRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface MainRouterModule {

    @Binds
    fun bindMainRouter(router: DefaultMainRouter): MainRouter

}