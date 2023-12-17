package com.andreikslpv.mtgcollection.glue.sets.di

import com.andreikslpv.domain_sets.SetsRouter
import com.andreikslpv.mtgcollection.glue.sets.AdapterSetsRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SetsRouterModule {

    @Binds
    fun bindSetsRouter(router: AdapterSetsRouter): SetsRouter
}