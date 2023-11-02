package com.andreikslpv.mtgcollection.glue.auth.di

import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.mtgcollection.glue.auth.AuthExternalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthRepositoriesModule {

    @Binds
    @Singleton
    fun bindAuthExternalRepository(authExternalRepository: AuthExternalRepositoryImpl): AuthExternalRepository
}