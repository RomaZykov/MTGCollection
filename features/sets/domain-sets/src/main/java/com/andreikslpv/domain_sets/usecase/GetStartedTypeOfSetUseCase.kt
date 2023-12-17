package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsExternalRepository
import javax.inject.Inject

class GetStartedTypeOfSetUseCase @Inject constructor(
    private val setsExternalRepository: SetsExternalRepository,
) {

    operator fun invoke() = setsExternalRepository.getStartedTypeOfSet()
}