package com.andreikslpv.mtgcollection.glue.navigation.di

import com.andreikslpv.mtgcollection.glue.navigation.DefaultDestinationsProvider
import com.andreikslpv.navigation.DestinationsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface StartDestinationProviderModule {

    @Binds
    fun bindStartDestinationProvider(
        startDestinationProvider: DefaultDestinationsProvider
    ): DestinationsProvider

}