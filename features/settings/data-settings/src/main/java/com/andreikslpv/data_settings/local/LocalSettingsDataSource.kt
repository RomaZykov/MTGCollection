package com.andreikslpv.data_settings.local

import com.andreikslpv.domain_settings.entities.AppSetting
import kotlinx.coroutines.flow.Flow

interface LocalSettingsDataSource {

    fun getSettingValue(setting: AppSetting): Any

    fun putSettingValue(setting: AppSetting, value: Any)

    fun observeSetting(setting: AppSetting): Flow<Any>

}