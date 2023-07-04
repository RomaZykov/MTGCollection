package com.andreikslpv.mtgcollection.glue.navigation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MainRepositoriesModule {

//    @Binds
//    fun bindGetCurrentUsernameRepository(
//        getCurrentUsernameRepository: AdapterGetCurrentUsernameRepository
//    ): GetCurrentUsernameRepository
//
//    @Binds
//    fun bindGetCartItemCountRepository(
//        getCartItemCountRepository: AdapterGetCartItemCountRepository
//    ): GetCartItemCountRepository

}