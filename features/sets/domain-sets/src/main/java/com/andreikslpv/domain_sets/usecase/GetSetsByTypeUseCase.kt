package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsRepository
import javax.inject.Inject

class GetSetsByTypeUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {

    fun execute(type: String) = setsRepository.getSetsByType(type)
}