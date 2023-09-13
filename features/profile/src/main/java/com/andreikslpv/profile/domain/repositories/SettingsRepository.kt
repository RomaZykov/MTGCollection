package com.andreikslpv.profile.domain.repositories

import kotlinx.coroutines.flow.MutableStateFlow

interface SettingsRepository {

    suspend fun getTypesOfSet(): MutableStateFlow<List<String>>

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

}