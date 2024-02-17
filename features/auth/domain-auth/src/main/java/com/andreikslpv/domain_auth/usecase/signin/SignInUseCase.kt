package com.andreikslpv.domain_auth.usecase.signin

import com.andreikslpv.domain_auth.repositories.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(idToken: String) = authRepository.signIn(idToken)

}