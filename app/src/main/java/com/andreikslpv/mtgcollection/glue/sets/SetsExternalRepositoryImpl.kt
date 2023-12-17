package com.andreikslpv.mtgcollection.glue.sets

import com.andreikslpv.domain_sets.SetsExternalRepository
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetsExternalRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : SetsExternalRepository {

    override fun getStartedTypeOfSet() = settingsRepository.getStartedTypeOfSet()

    override suspend fun isNeedToUpdateTypesOfSet(): Flow<Int> =
        settingsRepository.isNeedToUpdateTypesOfSet()

    override fun getDefaultMatchValue() = settingsRepository.getDefaultMatchValue()

    override fun setVersionForTypesOfSet(newVersion: Int) =
        settingsRepository.setVersionForTypesOfSet(newVersion)

    override fun refreshTypesOfSet() = settingsRepository.refreshTypesOfSet()

}