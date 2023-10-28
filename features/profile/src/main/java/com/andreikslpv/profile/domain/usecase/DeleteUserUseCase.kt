package com.andreikslpv.profile.domain.usecase

import com.andreikslpv.common.Constants
import com.andreikslpv.common.Response
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {

    fun execute(idToken: String) = flow {
        try {
            emit(Response.Loading)
            val uid = profileRepository.getCurrentUser()?.uid ?: ""
            if (uid.isNotBlank()) {
                profileRepository.deleteUserInDb(uid).collect { emit(it) }
                profileRepository.removeAllFromCollection(uid).collect { emit(it) }
                profileRepository.deleteUsersPhotoInDb(uid).collect { emit(it) }
            }
            profileRepository.deleteUserInAuth(idToken).collect { emit(it) }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_AUTH))
        }
    }
}