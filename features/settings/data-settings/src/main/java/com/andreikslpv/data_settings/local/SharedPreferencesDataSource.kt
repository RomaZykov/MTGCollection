package com.andreikslpv.data_settings.local

import android.content.Context
import android.content.SharedPreferences
import com.andreikslpv.domain_settings.entities.AppSetting

class SharedPreferencesDataSource(context: Context) : LocalSettingsDataSource {

    private val preference: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun getSettingValue(setting: AppSetting) =
        preference.getItem(setting.key, setting.defaultValue)

    override fun putSettingValue(setting: AppSetting, value: Any) =
        preference.putItem(setting, value)

    override fun observeSetting(setting: AppSetting) =
        preference.observeKey(setting.key, setting.defaultValue)
}