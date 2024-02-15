package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsExternalRepository
import com.andreikslpv.domain_sets.SetsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateTypesOfSetUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
    private val setsExternalRepository: SetsExternalRepository,
) {
    suspend operator fun invoke(forcedUpdate: Boolean) = withContext(Dispatchers.IO) {
        setsExternalRepository.getRemoteVersionForTypesOfSet().collect { remoteVersion ->
            if ((remoteVersion != setsExternalRepository.getLocaleVersionForTypesOfSet()) || forcedUpdate)
            // if is need, get types of set in remote databse
                setsRepository.getTypesOfSetInRemoteDb().collect { newTypes ->
                    // .. save it to local database
                    setsRepository.updateTypesInDb(newTypes)
                    // .. update version of types in local preferences storage
                    setsExternalRepository.setVersionForTypesOfSet(remoteVersion)
                }
        }
    }

}