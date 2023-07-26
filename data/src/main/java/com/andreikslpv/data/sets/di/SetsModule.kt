package com.andreikslpv.data.sets.di

import com.andreikslpv.data.sets.SetsDataRepository
import com.andreikslpv.data.sets.SetsDataRepositoryImpl
import com.andreikslpv.data.sets.datasource.SetsCacheDataSource
import com.andreikslpv.data.sets.datasource.SetsRoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SetsModule {

    @Provides
    @Singleton
    fun providesSetsCacheDataSource(setsCacheDataSource: SetsRoomDataSource): SetsCacheDataSource {
        return setsCacheDataSource
    }

    @Provides
    @Singleton
    fun providesSetsDataRepository(setsDataRepository: SetsDataRepositoryImpl): SetsDataRepository {
        return setsDataRepository
    }
}