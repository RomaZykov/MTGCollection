package com.andreikslpv.mtgcollection.glue.profile

import android.net.Uri
import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.repositories.AuthRepository
import com.andreikslpv.mtgcollection.glue.cards.AccountDataToFeatureModelMapper
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val authDataRepository: AuthRepository,
) : ProfileRepository {

    override fun signOut() = authDataRepository.signOut()





    override suspend fun deleteUsersPhotoInDb(uid: String) =
        authDataRepository.deleteUsersPhotoInDb(uid)

    override suspend fun deleteUserInAuth(idToken: String) =
        authDataRepository.deleteUserInAuth(idToken)

    override suspend fun linkAnonymousWithCredential(idToken: String) = flow {
        authDataRepository.linkAnonymousWithCredential(idToken).collect {
            when (it) {
                Response.Loading -> emit(Response.Loading)
                is Response.Failure -> emit(Response.Failure(it.errorMessage))
                is Response.Success -> emit(Response.Success(AccountDataToFeatureModelMapper.map(it.data)))
            }
        }
    }


    override fun getCurrentUser() =
        AccountDataToFeatureModelMapper.map(authDataRepository.getCurrentUser())













    override suspend fun editUserName(newName: String) = authDataRepository.editUserName(newName)

    override suspend fun changeUserPhoto(localUri: Uri) =
        authDataRepository.changeUserPhoto(localUri.toString())

}