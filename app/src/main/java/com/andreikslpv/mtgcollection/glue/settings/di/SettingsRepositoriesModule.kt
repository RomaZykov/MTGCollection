package com.andreikslpv.mtgcollection.glue.settings.di

import com.andreikslpv.domain_settings.repositories.SettingsExternalRepository
import com.andreikslpv.mtgcollection.glue.settings.SettingsExternalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingsRepositoriesModule {

    @Binds
    @Singleton
    fun bindSettingsExternalRepository(settingsRepository: SettingsExternalRepositoryImpl): SettingsExternalRepository
}