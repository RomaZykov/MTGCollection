package com.andreikslpv.domain_auth.repositories

import com.andreikslpv.common.Response
import com.andreikslpv.domain_auth.entities.AccountDataEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(idToken: String): Flow<Response<Boolean>>

    suspend fun signInAnonymously(): Flow<Response<Boolean>>

    suspend fun linkAnonymousWithCredential(idToken: String): Flow<Response<AccountDataEntity?>>

    fun signOut(): Flow<Response<Void>>

    fun getAuthState(): Flow<Boolean>

    fun getCurrentUser(): AccountDataEntity?

    suspend fun deleteUserInAuth(idToken: String): Flow<Response<Boolean>>

    suspend fun deleteUsersPhotoInDb(uid: String): Flow<Response<Boolean>>

    suspend fun editUserName(newName: String): Flow<Response<Boolean>>

    suspend fun changeUserPhoto(localUri: String): Flow<Response<Boolean>>

    suspend fun getPrivacyPolicy(): Flow<Response<String>>

}