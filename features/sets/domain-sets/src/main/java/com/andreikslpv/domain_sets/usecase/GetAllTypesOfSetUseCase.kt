package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsExternalRepository
import com.andreikslpv.domain_sets.SetsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllTypesOfSetUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
    private val setsExternalRepository: SetsExternalRepository,
    private val updateTypesOfSetUseCase: UpdateTypesOfSetUseCase,
) {

    private var canBeUpdated = true

    suspend operator fun invoke() = flow {
        updateTypesOfSetUseCase(false)
        setsRepository.getAllTypes().collect { response ->
            // if received list of types is empty, try one time make forced update
            if (response.isEmpty() && canBeUpdated) {
                canBeUpdated = false
                setsExternalRepository.refreshTypesOfSet()
                updateTypesOfSetUseCase(true)
            } else {
                emit(response)
            }
        }
    }.flowOn(Dispatchers.IO)

}