package com.andreikslpv.mtgcollection.glue.settings

import com.andreikslpv.domain_sets.SetsRepository
import com.andreikslpv.domain_settings.repositories.SettingsExternalRepository
import javax.inject.Inject

class SettingsExternalRepositoryImpl @Inject constructor(
    private val setsDataRepository: SetsRepository,
) : SettingsExternalRepository {

    override suspend fun getTypesOfSet() = setsDataRepository.getNamesOfAllTypesOfSet()

}