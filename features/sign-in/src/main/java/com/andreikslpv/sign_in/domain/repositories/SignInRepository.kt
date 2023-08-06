package com.andreikslpv.sign_in.domain.repositories

import com.andreikslpv.common.Response
import com.andreikslpv.sign_in.domain.entities.AccountFeatureEntity
import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    suspend fun signIn(): Flow<Response<Boolean>>

    fun getCurrentUser(): AccountFeatureEntity?

    suspend fun createUser(uid: String): Flow<Response<Boolean>>

}