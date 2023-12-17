package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsExternalRepository
import com.andreikslpv.domain_sets.SetsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckForUpdatesUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
    private val setsExternalRepository: SetsExternalRepository,
) {
    suspend operator fun invoke() {
        // start checking updates for types of set
        CoroutineScope(Dispatchers.IO).launch {
            // first, check if there is a need
            setsExternalRepository.isNeedToUpdateTypesOfSet().collectLatest { versionOfTypes ->
                if (versionOfTypes != setsExternalRepository.getDefaultMatchValue())
                // if is need, get types of set in remote databse
                    setsRepository.getTypesOfSetInRemoteDb().collectLatest { newTypes ->
                        // .. save it to local database
                        setsRepository.updateTypesInDb(newTypes)
                        // .. update version of types in local preferences storage
                        setsExternalRepository.setVersionForTypesOfSet(versionOfTypes)
                    }
            }
        }
    }

}