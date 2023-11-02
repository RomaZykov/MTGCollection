package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsRepository
import javax.inject.Inject

class GetTypesOfSetUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {

    suspend fun execute() = setsRepository.getTypesOfSet()

}