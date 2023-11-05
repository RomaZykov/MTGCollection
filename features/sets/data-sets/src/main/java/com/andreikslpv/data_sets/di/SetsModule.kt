package com.andreikslpv.data_sets.di

import com.andreikslpv.data_sets.SetsRepositoryImpl
import com.andreikslpv.data_sets.datasource.SetsApiDataSource
import com.andreikslpv.data_sets.datasource.SetsCacheDataSource
import com.andreikslpv.data_sets.datasource.SetsFirebaseDataSource
import com.andreikslpv.data_sets.datasource.SetsRoomDataSource
import com.andreikslpv.data_sets.datasource.TypesDataSource
import com.andreikslpv.data_sets.datasource.TypesInMemoryDataSource
import com.andreikslpv.domain_sets.SetsRepository
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
    fun providesSetsDataRepository(setsDataRepository: SetsRepositoryImpl): SetsRepository {
        return setsDataRepository
    }
}