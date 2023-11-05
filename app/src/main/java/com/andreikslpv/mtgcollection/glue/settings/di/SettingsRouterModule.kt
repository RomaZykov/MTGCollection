package com.andreikslpv.mtgcollection.glue.settings.di

import com.andreikslpv.domain_settings.repositories.SettingsRouter
import com.andreikslpv.mtgcollection.glue.settings.AdapterSettingsRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SettingsRouterModule {

    @Binds
    fun bindSettingsRouter(settingsRouter: AdapterSettingsRouter): SettingsRouter

}