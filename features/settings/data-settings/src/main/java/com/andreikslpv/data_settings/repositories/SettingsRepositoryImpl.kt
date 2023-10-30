package com.andreikslpv.data_settings.repositories

import com.andreikslpv.common.SettingsDataSource
import com.andreikslpv.data_settings.ProjectSettings
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
): SettingsRepository {

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

}