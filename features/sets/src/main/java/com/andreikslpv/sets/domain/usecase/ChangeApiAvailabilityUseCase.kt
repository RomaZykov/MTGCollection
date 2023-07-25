package com.andreikslpv.sets.domain.usecase

import com.andreikslpv.sets.domain.repositories.SetsRepository
import javax.inject.Inject

class ChangeApiAvailabilityUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {

    fun execute(newStatus: Boolean) {
        setsRepository.changeApiAvailability(newStatus)
    }
}