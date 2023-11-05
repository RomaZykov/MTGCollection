package com.andreikslpv.common

interface SettingsDataSource {

    fun getSettingsValue(setting: SettingsBaseValue): Any?

    fun putSettingsValue(setting: SettingsBaseValue, value: Any)

}