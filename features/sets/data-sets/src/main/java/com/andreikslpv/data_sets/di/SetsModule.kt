package com.andreikslpv.data_sets.di

import com.andreikslpv.data_sets.SetsRepositoryImpl
import com.andreikslpv.data_sets.datasource.SetsDataSource
import com.andreikslpv.data_sets.datasource.SetsFirebaseDataSource
import com.andreikslpv.data_sets.datasource.TypesDataSource
import com.andreikslpv.data_sets.datasource.TypesRoomDataSource
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
    fun providesSetsDataSource(setsDataSource: SetsFirebaseDataSource): SetsDataSource {
        return setsDataSource
    }

    @Provides
    @Singleton
    fun providesTypesDataSource(typesDataSource: TypesRoomDataSource): TypesDataSource {
        return typesDataSource
    }

    @Provides
    @Singleton
    fun providesSetsDataRepository(setsDataRepository: SetsRepositoryImpl): SetsRepository {
        return setsDataRepository
    }
}