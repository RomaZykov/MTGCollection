package com.andreikslpv.data_settings

import com.andreikslpv.data_settings.local.LocalSettingsDataSource
import com.andreikslpv.data_settings.remote.RemoteSettingsDataSource
import com.andreikslpv.domain_settings.entities.SettingStartSetsType
import com.andreikslpv.domain_settings.entities.SettingVersionSetsType
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val localSettingsDataSource: LocalSettingsDataSource,
    private val remoteSettingsDataSource: RemoteSettingsDataSource,
) : SettingsRepository {

    override fun getStartedTypeOfSet() =
        localSettingsDataSource.getSettingValue(SettingStartSetsType()) as String

    override fun setStartedTypeOfSet(type: String) =
        localSettingsDataSource.putSettingValue(SettingStartSetsType(), type)

    override fun setVersionForTypesOfSet(newVersion: Int) =
        localSettingsDataSource.putSettingValue(SettingVersionSetsType(), newVersion)

    override fun refreshTypesOfSet() =
        setVersionForTypesOfSet(SettingVersionSetsType().defaultValue as Int)

    override suspend fun getPrivacyPolicy() = remoteSettingsDataSource.getPrivacyPolicy()

    override suspend fun getRemoteVersionForTypesOfSet() =
        remoteSettingsDataSource.getVersionSetsType()

    override fun getLocaleVersionForTypesOfSet() =
        localSettingsDataSource.getSettingValue(SettingVersionSetsType()) as Int

}