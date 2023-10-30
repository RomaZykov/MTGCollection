package com.andreikslpv.mtgcollection.glue.settings.di

import com.andreikslpv.domain_settings.repositories.SettingsRepository
import com.andreikslpv.domain_settings.repositories.SettingsRouter
import com.andreikslpv.mtgcollection.glue.settings.AdapterSettingsRepository
import com.andreikslpv.mtgcollection.glue.settings.AdapterSettingsRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SettingsAppModule {

    @Binds
    @Singleton
    fun bindSettingsRouter(settingsRouter: AdapterSettingsRouter): SettingsRouter

    @Binds
    @Singleton
    fun bindSettingsRepository(settingsRepository: AdapterSettingsRepository): SettingsRepository

}