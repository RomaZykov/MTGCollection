package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsRepository
import javax.inject.Inject

class GetTypeCodeByNameUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {
    operator fun invoke(nameOfType: String) = setsRepository.getTypeCodeByName(nameOfType)
}