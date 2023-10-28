package com.andreikslpv.sign_in.domain.usecase

import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {

    suspend fun execute(idToken: String) = signInRepository.signIn(idToken)

}