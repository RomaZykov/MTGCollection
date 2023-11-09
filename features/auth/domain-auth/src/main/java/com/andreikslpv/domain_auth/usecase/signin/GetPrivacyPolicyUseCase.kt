package com.andreikslpv.domain_auth.usecase.signin

import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import javax.inject.Inject

class GetPrivacyPolicyUseCase @Inject constructor(
    private val authExternalRepository: AuthExternalRepository,
) {

    suspend fun execute() = authExternalRepository.getPrivacyPolicy()
}