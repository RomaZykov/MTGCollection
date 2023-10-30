package com.andreikslpv.domain_settings.repositories

interface SettingsRepository {

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

}