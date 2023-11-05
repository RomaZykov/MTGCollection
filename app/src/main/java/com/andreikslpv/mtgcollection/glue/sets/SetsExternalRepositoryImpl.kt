package com.andreikslpv.mtgcollection.glue.sets

import com.andreikslpv.domain_sets.SetsExternalRepository
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import javax.inject.Inject

class SetsExternalRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : SetsExternalRepository {

    override fun getStartedTypeOfSet() = settingsRepository.getStartedTypeOfSet()
}