package com.andreikslpv.sets.domain.usecase

import com.andreikslpv.sets.domain.repositories.SetsRepository
import javax.inject.Inject

class GetTypesOfSetUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {

    suspend fun execute() = setsRepository.getTypesOfSet()

}