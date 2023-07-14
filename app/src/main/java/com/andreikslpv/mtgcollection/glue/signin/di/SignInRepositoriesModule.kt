package com.andreikslpv.mtgcollection.glue.signin.di

import com.andreikslpv.mtgcollection.glue.signin.repositories.AdapterAuthRepository
import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SignInRepositoriesModule {

    @Binds
    @Singleton
    fun bindAuthRepository(
        signInRepository: AdapterAuthRepository
    ): SignInRepository

}