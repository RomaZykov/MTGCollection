package com.andreikslpv.sign_in.domain.usecase

import com.andreikslpv.common.Constants
import com.andreikslpv.common.Response
import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {

    suspend fun execute() = flow {
        try {
            emit(Response.Loading)
            signInRepository.getCurrentUser()?.let { user ->
                signInRepository.createUser(user.uid).collect { response ->
                    emit(response)
                }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_AUTH))
        }
    }

}