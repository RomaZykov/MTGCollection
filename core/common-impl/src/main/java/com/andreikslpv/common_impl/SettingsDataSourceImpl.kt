package com.andreikslpv.common_impl

import android.content.Context
import android.content.SharedPreferences
import com.andreikslpv.common.BaseSettingsValue
import com.andreikslpv.common.SettingsDataSource

class SettingsDataSourceImpl(
    settings: List<BaseSettingsValue>,
    context: Context,
) : SettingsDataSource {

    private val preference: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val firstLaunchKey = "first_launch"
    private val firstLaunchDefaultValue = true

    init {
        //Логика для первого запуска приложения, чтобы положить дефолтные настройки,
        if (preference.getBoolean(firstLaunchKey, firstLaunchDefaultValue)) {
            settings.forEach {
                putSettingsValue(it, it.defaultValue)
            }
            preference.edit().putBoolean(firstLaunchKey, false).apply()
        }
    }

    override fun getSettingsValue(setting: BaseSettingsValue): Any? {
        return when (setting) {
            is IntSettingsValue -> {
                try {
                    preference.getInt(setting.key, setting.defaultValue)
                } catch (_: Exception) {
                }
            }

            is StringSettingsValue -> {
                try {
                    preference.getString(setting.key, setting.defaultValue)
                } catch (_: Exception) {
                }
            }

            else -> null
        }
    }

    override fun putSettingsValue(setting: BaseSettingsValue, value: Any) {
        when (setting) {
            is IntSettingsValue -> {
                try {
                    preference.edit().putInt(setting.key, (value as Int)).apply()
                } catch (_: Exception) {
                }
            }

            is StringSettingsValue -> {
                try {
                    preference.edit().putString(setting.key, (value as String)).apply()
                } catch (_: Exception) {
                }
            }
        }
    }
}