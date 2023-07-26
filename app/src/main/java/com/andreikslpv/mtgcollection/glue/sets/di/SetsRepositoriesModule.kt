package com.andreikslpv.mtgcollection.glue.sets.di

import com.andreikslpv.mtgcollection.glue.sets.repositories.AdapterSetsRepository
import com.andreikslpv.sets.domain.repositories.SetsRepository
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
    fun bindSetsRepository(
        setsRepository: AdapterSetsRepository
    ): SetsRepository
}