package com.andreikslpv.data_settings

import com.andreikslpv.data_settings.local.LocalSettingsDataSource
import com.andreikslpv.data_settings.remote.RemoteSettingsDataSource
import com.andreikslpv.domain_settings.entities.SettingStartSetsType
import com.andreikslpv.domain_settings.entities.SettingVersionSetsType
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val localSettingsDataSource: LocalSettingsDataSource,
    private val remoteSettingsDataSource: RemoteSettingsDataSource,
) : SettingsRepository {

    override fun getStartedTypeOfSet() =
        localSettingsDataSource.getSettingValue(SettingStartSetsType()) as String

    override fun setStartedTypeOfSet(type: String) =
        localSettingsDataSource.putSettingValue(SettingStartSetsType(), type)

    override suspend fun isNeedToUpdateTypesOfSet() = flow {
        try {
            val localValue =
                localSettingsDataSource.getSettingValue(SettingVersionSetsType()) as Int
            val remoteValue = remoteSettingsDataSource.getVersionSetsType()
            emit(
                if (localValue != remoteValue) remoteValue else NO_UPDATE_NEEDED
            )
        } catch (e: Exception) {
            emit(NO_UPDATE_NEEDED)
        }
    }

    override fun setVersionForTypesOfSet(newVersion: Int) =
        localSettingsDataSource.putSettingValue(SettingVersionSetsType(), newVersion)

    override fun refreshTypesOfSet() =
        setVersionForTypesOfSet(SettingVersionSetsType().defaultValue as Int)

    override fun getDefaultMatchValue() = NO_UPDATE_NEEDED

    override suspend fun getPrivacyPolicy() = remoteSettingsDataSource.getPrivacyPolicy()

    companion object {
        const val NO_UPDATE_NEEDED = -1
    }

}