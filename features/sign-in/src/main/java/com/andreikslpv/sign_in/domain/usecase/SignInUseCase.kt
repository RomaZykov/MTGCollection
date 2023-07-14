package com.andreikslpv.sign_in.domain.usecase

import com.andreikslpv.common.Constants
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {

    suspend fun execute() = flow {
        try {
            emit(Response.Loading)
            signInRepository.signIn().collect { response ->
                when(response) {
                    is Response.Loading -> emit(Response.Loading)
                    is Response.Failure -> emit(Response.Failure(response.errorMessage))
                    is Response.Success -> {
                        emit(Response.Success(response.data))
                        if (response.data) {
                            // New user
                            Core.logger.log(signInRepository.getCurrentUser()?.displayName ?: "name")
                            TODO("create user in DB")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_AUTH))
        }
    }

}