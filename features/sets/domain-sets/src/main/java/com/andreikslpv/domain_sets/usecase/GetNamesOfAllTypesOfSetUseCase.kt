package com.andreikslpv.domain_sets.usecase

import com.andreikslpv.domain_sets.SetsRepository
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNamesOfAllTypesOfSetUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
    private val checkForUpdatesUseCase: CheckForUpdatesUseCase,
) {

    suspend operator fun invoke(): Flow<List<TypeOfSetEntity>> {
        checkForUpdatesUseCase()
        return setsRepository.getAllTypes()
    }

}