package com.andreikslpv.mtgcollection.glue.signin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignInRepositoriesModule {

//    @Binds
//    fun bindAuthRepository(
//        authServiceRepository: AdapterAuthServiceRepository
//    ): AuthServiceRepository
//
//    @Binds
//    fun bindAuthTokenRepository(
//        authTokenRepository: AdapterAuthTokenRepository
//    ): AuthTokenRepository
//
//    @Binds
//    fun bindProfileRepository(
//        profileRepository: AdapterProfileRepository
//    ): ProfileRepository

}