package com.andreikslpv.mtgcollection.glue.profile

import com.andreikslpv.data.sets.SetsDataRepository
import com.andreikslpv.profile.domain.repositories.SettingsRepository
import javax.inject.Inject

class AdapterSettingsRepository @Inject constructor(
    private val setsDataRepository: SetsDataRepository,
): SettingsRepository {

    override suspend fun getTypesOfSet() = setsDataRepository.getTypesOfSet()

    override fun getStartedTypeOfSet(): String {
        return setsDataRepository.getStartedTypeOfSet()
    }

    override fun setStartedTypeOfSet(type: String) {
        return setsDataRepository.setStartedTypeOfSet(type)
    }
}