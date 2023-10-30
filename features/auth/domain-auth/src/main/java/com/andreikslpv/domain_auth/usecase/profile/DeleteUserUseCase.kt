package com.andreikslpv.domain_auth.usecase.profile

import com.andreikslpv.common.Constants.UNKNOWN_ERROR
import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authExternalRepository: AuthExternalRepository,
) {

    fun execute(idToken: String) = flow {
        try {
            emit(Response.Loading)
            val uid = authRepository.getCurrentUser()?.uid ?: ""
            if (uid.isNotBlank()) {
                authExternalRepository.deleteUserInDb(uid).collect { emit(it) }
                authExternalRepository.removeAllFromCollection(uid).collect { emit(it) }
                authRepository.deleteUsersPhotoInDb(uid).collect { emit(it) }
            }
            authRepository.deleteUserInAuth(idToken).collect { emit(it) }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: UNKNOWN_ERROR))
        }
    }
}