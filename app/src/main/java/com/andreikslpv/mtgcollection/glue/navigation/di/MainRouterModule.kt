package com.andreikslpv.mtgcollection.glue.navigation.di

import com.andreikslpv.mtgcollection.glue.navigation.DefaultMainRouter
import com.andreikslpv.navigation.presentation.MainRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface MainRouterModule {

    @Binds
    fun bindMainRouter(router: DefaultMainRouter): MainRouter

}