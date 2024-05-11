package com.andreikslpv.navigation.domain.usecase

import com.andreikslpv.navigation.domain.repositories.MainRepository
import javax.inject.Inject

class UpdateSetsUseCase @Inject constructor(private val mainRepository: MainRepository) {

    suspend operator fun invoke() = mainRepository.updateSets()
}