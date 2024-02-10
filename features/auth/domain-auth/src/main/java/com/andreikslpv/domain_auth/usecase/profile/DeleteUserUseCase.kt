package com.andreikslpv.domain_auth.usecase.profile

import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authExternalRepository: AuthExternalRepository,
) {

    operator fun invoke(idToken: String) = channelFlow {
        send(Response.Loading)
        val uid = authRepository.getCurrentUser()?.uid ?: ""
        if (uid.isNotBlank()) {
            authExternalRepository.deleteUserInDb(uid).collect { send(it) }
            authExternalRepository.removeAllFromCollection(uid).collect { send(it) }
            authRepository.deleteUsersPhotoInDb(uid)
        }
        authRepository.deleteUserInAuth(idToken).collect { send(it) }
    }.flowOn(Dispatchers.IO)
}