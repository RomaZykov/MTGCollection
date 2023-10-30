package com.andreikslpv.mtgcollection.glue.settings

import com.andreikslpv.data.sets.SetsDataRepository
import com.andreikslpv.domain_settings.repositories.SettingsExternalRepository
import javax.inject.Inject

class AdapterSettingsRepository @Inject constructor(
    private val setsDataRepository: SetsDataRepository,
): SettingsExternalRepository {

    override suspend fun getTypesOfSet() = setsDataRepository.getTypesOfSet()

}