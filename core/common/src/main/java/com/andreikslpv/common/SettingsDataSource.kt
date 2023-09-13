package com.andreikslpv.common

interface SettingsDataSource {

    fun getSettingsValue(setting: BaseSettingsValue): Any?

    fun putSettingsValue(setting: BaseSettingsValue, value: Any)

}