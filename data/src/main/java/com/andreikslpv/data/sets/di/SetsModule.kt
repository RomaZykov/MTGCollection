package com.andreikslpv.data.sets.di

import com.andreikslpv.data.sets.SetsDataRepository
import com.andreikslpv.data.sets.SetsDataRepositoryImpl
import com.andreikslpv.data.sets.datasource.SetsApiDataSource
import com.andreikslpv.data.sets.datasource.SetsCacheDataSource
import com.andreikslpv.data.sets.datasource.SetsFirebaseDataSource
import com.andreikslpv.data.sets.datasource.SetsRoomDataSource
import com.andreikslpv.data.sets.datasource.TypesDataSource
import com.andreikslpv.data.sets.datasource.TypesInMemoryDataSource
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
    fun providesSetsApiDataSource(setsApiDataSource: SetsFirebaseDataSource): SetsApiDataSource {
        return setsApiDataSource
    }

    @Provides
    @Singleton
    fun providesTypesDataSource(typesDataSource: TypesInMemoryDataSource): TypesDataSource {
        return typesDataSource
    }

    @Provides
    @Singleton
    fun providesSetsDataRepository(setsDataRepository: SetsDataRepositoryImpl): SetsDataRepository {
        return setsDataRepository
    }
}