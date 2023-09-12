package com.andreikslpv.mtgcollection.glue.profile.di

import com.andreikslpv.mtgcollection.glue.profile.AdapterProfileRepository
import com.andreikslpv.mtgcollection.glue.profile.AdapterSettingsRepository
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import com.andreikslpv.profile.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepositoriesModule {

    @Binds
    @Singleton
    fun bindProfileRepository(
        profileRepository: AdapterProfileRepository
    ): ProfileRepository

    @Binds
    @Singleton
    fun bindSettingsRepository(
        settingsRepository: AdapterSettingsRepository
    ): SettingsRepository

}