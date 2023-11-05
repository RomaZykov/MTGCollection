package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsRepository
import javax.inject.Inject

class ChangeApiAvailabilityUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {

    fun execute(newStatus: Boolean) = setsRepository.changeApiAvailability(newStatus)
}