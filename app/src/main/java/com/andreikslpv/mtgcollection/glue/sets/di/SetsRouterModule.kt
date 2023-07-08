package com.andreikslpv.mtgcollection.glue.sets.di

import com.andreikslpv.sets.presentation.SetsRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SetsRouterModule {

    @Binds
    fun bindSetsRouter(
        setsRouter: SetsRouter,
    ): SetsRouter
}