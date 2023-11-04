package com.andreikslpv.domain_auth.usecase.signin

import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authExternalRepository: AuthExternalRepository,
) {

    suspend fun execute() = flow {
        try {
            emit(Response.Loading)
            authRepository.getCurrentUser()?.let { user ->
                authExternalRepository.createUser(user.uid).collect { response ->
                    emit(response)
                }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

}