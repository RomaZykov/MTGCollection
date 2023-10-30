package com.andreikslpv.domain_settings.repositories

import kotlinx.coroutines.flow.MutableStateFlow

interface SettingsExternalRepository {

    suspend fun getTypesOfSet(): MutableStateFlow<List<String>>

}