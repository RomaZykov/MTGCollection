package com.andreikslpv.sets.domain.usecase

import androidx.paging.PagingData
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import com.andreikslpv.sets.domain.repositories.SetsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSetsByTypeUseCase @Inject constructor(
    private val setsRepository: SetsRepository,
) {

    fun execute(type: String): Flow<PagingData<SetFeatureModel>> {
        return setsRepository.getSetsByType(type)
    }
}