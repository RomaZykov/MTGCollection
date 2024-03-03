package com.andreikslpv.mtgcollection.glue.core.di

import com.andreikslpv.domain.repositories.CoreExternalRepository
import com.andreikslpv.mtgcollection.glue.core.CoreExternalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoreRepositoriesModule {

    @Binds
    @Singleton
    fun bindCoreExternalRepository(coreExternalRepository: CoreExternalRepositoryImpl): CoreExternalRepository
}