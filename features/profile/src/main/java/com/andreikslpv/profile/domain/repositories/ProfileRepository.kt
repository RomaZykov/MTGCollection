package com.andreikslpv.profile.domain.repositories

import android.net.Uri
import com.andreikslpv.common.Response
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun signOut(): Flow<Response<Void>>





    suspend fun deleteUsersPhotoInDb(uid: String): Flow<Response<Boolean>>

    suspend fun deleteUserInAuth(idToken: String): Flow<Response<Boolean>>

    suspend fun linkAnonymousWithCredential(idToken: String): Flow<Response<AccountFeatureEntity?>>

    fun getCurrentUser(): AccountFeatureEntity?













    suspend fun editUserName(newName: String): Flow<Response<Boolean>>

    suspend fun changeUserPhoto(localUri: Uri): Flow<Response<Boolean>>

}