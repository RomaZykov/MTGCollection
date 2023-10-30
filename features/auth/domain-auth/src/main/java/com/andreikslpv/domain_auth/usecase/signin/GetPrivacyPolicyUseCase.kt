package com.andreikslpv.domain_auth.usecase.signin

import com.andreikslpv.domain_auth.repositories.AuthRepository
import javax.inject.Inject

class GetPrivacyPolicyUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend fun execute() = authRepository.getPrivacyPolicy()
}