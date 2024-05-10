package com.andreikslpv.data_sets.di

import com.andreikslpv.data_sets.SetsRepositoryImpl
import com.andreikslpv.data_sets.datasource.SetsDataSource
import com.andreikslpv.data_sets.datasource.SetsRoomDataSource
import com.andreikslpv.data_sets.datasource.TypesDataSource
import com.andreikslpv.data_sets.datasource.TypesRoomDataSource
import com.andreikslpv.data_sets.services.SetsApi
import com.andreikslpv.domain_sets.SetsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SetsModule {

    @Provides
    @Singleton
    fun providesSetsDataSource(setsDataSource: SetsRoomDataSource): SetsDataSource {
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

    @Provides
    fun provideSetsApi(retrofit: Retrofit): SetsApi {
        return retrofit.create(SetsApi::class.java)
    }
}