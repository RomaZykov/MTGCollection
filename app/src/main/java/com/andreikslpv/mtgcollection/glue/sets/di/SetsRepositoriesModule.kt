package com.andreikslpv.mtgcollection.glue.sets.di

import com.andreikslpv.domain_sets.SetsExternalRepository
import com.andreikslpv.mtgcollection.glue.sets.SetsExternalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SetsRepositoriesModule {

    @Binds
    @Singleton
    fun bindSetsExternalRepository(setsExternalRepository: SetsExternalRepositoryImpl): SetsExternalRepository
}