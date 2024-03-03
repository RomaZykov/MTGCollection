package com.andreikslpv.domain_auth.usecase.signin

import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authExternalRepository: AuthExternalRepository,
) {

    suspend operator fun invoke() = flow {
        emit(Response.Loading)
        authRepository.getCurrentUser()?.let { user ->
            authExternalRepository.createUser(user.uid).collect { emit(it) }
        }
    }.flowOn(Dispatchers.IO)

}