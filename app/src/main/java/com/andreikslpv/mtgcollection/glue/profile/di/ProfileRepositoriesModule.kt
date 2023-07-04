package com.andreikslpv.mtgcollection.glue.profile.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepositoriesModule {

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