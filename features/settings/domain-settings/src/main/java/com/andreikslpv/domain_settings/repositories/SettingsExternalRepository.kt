package com.andreikslpv.domain_settings.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsExternalRepository {

    suspend fun getTypesOfSet(): Flow<List<String>>

}