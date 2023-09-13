package com.andreikslpv.data.di

import android.content.Context
import com.andreikslpv.common.SettingsDataSource
import com.andreikslpv.common_impl.SettingsDataSourceImpl
import com.andreikslpv.data.settings.AllSettings
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
}
