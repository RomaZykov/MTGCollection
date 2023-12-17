package com.andreikslpv.data_settings.di

import android.content.Context
import com.andreikslpv.common.SettingsDataSource
import com.andreikslpv.common_impl.SettingsDataSourceImpl
import com.andreikslpv.data_settings.AllSettings
import com.andreikslpv.data_settings.SettingsRepositoryImpl
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsDataSource(
        @ApplicationContext applicationContext: Context,
    ): SettingsDataSource {
        return SettingsDataSourceImpl(AllSettings.settings, applicationContext)
    }

    @Provides
    @Singleton
    fun providesSettingsRepository(settingsRepository: SettingsRepositoryImpl): SettingsRepository {
        return settingsRepository
    }

    @Provides
    @Singleton
    fun provideRemoteConfigInstance(): FirebaseRemoteConfig {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val settings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(settings)
        return remoteConfig
    }
}
