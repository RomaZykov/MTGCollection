package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsRepository
import javax.inject.Inject

class GetSetsByCodeOfTypeUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {

    operator fun invoke(codeOfType: String) = setsRepository.getSetsByType(codeOfType)
}