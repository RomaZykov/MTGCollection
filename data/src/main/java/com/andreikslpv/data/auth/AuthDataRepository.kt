package com.andreikslpv.data.auth

import android.net.Uri
import com.andreikslpv.common.Response
import com.andreikslpv.data.auth.entities.AccountDataEntity
import kotlinx.coroutines.flow.Flow

interface AuthDataRepository {

    suspend fun signIn(idToken: String?): Flow<Response<Boolean>>

    fun signOut(): Flow<Response<Void>>

    fun getAuthState(): Flow<Boolean>

    fun getCurrentUser(): AccountDataEntity?

    suspend fun deleteUser(idToken: String?): Flow<Response<Boolean>>

    suspend fun editUserName(newName: String): Flow<Response<Boolean>>

    suspend fun changeUserPhoto(uri: Uri): Flow<Response<Boolean>>

}