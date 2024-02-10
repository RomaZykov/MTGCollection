package com.andreikslpv.domain_auth.usecase.signin

import com.andreikslpv.domain_auth.repositories.AuthRepository
import javax.inject.Inject

class SignInAnonymouslyUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke() = authRepository.signInAnonymously()

}