package com.andreikslpv.data_settings.repositories

import com.andreikslpv.common.Response
import com.andreikslpv.common.SettingsDataSource
import com.andreikslpv.data_settings.ProjectSettings
import com.andreikslpv.data_settings.RemoteConfigConstants.SETTING_PRIVACY_POLICY
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val remoteConfig: FirebaseRemoteConfig,
) : SettingsRepository {

    override fun getStartedTypeOfSet(): String {
        return try {
            (settingsDataSource.getSettingsValue(ProjectSettings.START_SETS_TYPE.value) as String)
        } catch (e: Exception) {
            (ProjectSettings.START_SETS_TYPE.value.defaultValue as String)
        }
    }

    override fun setStartedTypeOfSet(type: String) {
        settingsDataSource.putSettingsValue(ProjectSettings.START_SETS_TYPE.value, type)
    }

    override suspend fun isNeedToUpdateTypesOfSet() = flow {
        remoteConfig.fetchAndActivate().await().also {
            val localValue = try {
                (settingsDataSource.getSettingsValue(ProjectSettings.VERSION_SETS_TYPE.value) as Int)
            } catch (e: Exception) {
                (ProjectSettings.VERSION_SETS_TYPE.value.defaultValue as String)
            }
            val remoteValue =
                remoteConfig.getLong(ProjectSettings.VERSION_SETS_TYPE.value.key).toInt()
            emit(
                if (localValue != remoteValue) remoteValue else NO_UPDATE_NEEDED
            )
        }
    }

    override fun setVersionForTypesOfSet(newVersion: Int) {
        settingsDataSource.putSettingsValue(ProjectSettings.VERSION_SETS_TYPE.value, newVersion)
    }

    override fun getDefaultMatchValue() = NO_UPDATE_NEEDED

    override suspend fun getPrivacyPolicy() = flow {
        try {
            emit(Response.Loading)
            remoteConfig.fetchAndActivate().await().also {
                emit(Response.Success(remoteConfig.getString(SETTING_PRIVACY_POLICY)))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    companion object {
        const val NO_UPDATE_NEEDED = -1
    }

}