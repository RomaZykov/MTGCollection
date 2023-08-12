package com.andreikslpv.mtgcollection.glue.navigation.di

import com.andreikslpv.mtgcollection.glue.navigation.AdapterMainRepository
import com.andreikslpv.navigation.domain.repositories.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MainRepositoriesModule {

    @Binds
    @Singleton
    fun bindMainRepository(
        mainRepository: AdapterMainRepository
    ): MainRepository
}