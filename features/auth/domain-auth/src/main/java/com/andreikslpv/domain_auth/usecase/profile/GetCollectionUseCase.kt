package com.andreikslpv.domain_auth.usecase.profile

import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authExternalRepository: AuthExternalRepository,
) {

    operator fun invoke(): MutableStateFlow<List<String>> {
        val user = authRepository.getCurrentUser()
        return if (user != null) authExternalRepository.getCollection()
        else MutableStateFlow(emptyList())
    }
}