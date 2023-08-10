package com.andreikslpv.profile.domain.repositories

import android.net.Uri
import com.andreikslpv.common.Response
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun signOut(): Flow<Response<Void>>

    fun getAuthState(): Flow<Boolean>

    suspend fun deleteUser(): Flow<Response<Boolean>>

    fun getCurrentUser(): AccountFeatureEntity?

    suspend fun editUserName(newName: String): Flow<Response<Boolean>>

    suspend fun changeUserPhoto(uri: Uri): Flow<Response<Boolean>>

}