package com.andreikslpv.domain.usecase

import com.andreikslpv.domain.repositories.CoreExternalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val coreExternalRepository: CoreExternalRepository,
) {

    operator fun invoke(): MutableStateFlow<List<String>> {
        val userUid = coreExternalRepository.getCurrentUserUid()
        return if (userUid != null) coreExternalRepository.getCollection()
        else MutableStateFlow(emptyList())
    }
}