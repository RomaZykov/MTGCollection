package com.andreikslpv.sign_in.domain.repositories

import com.andreikslpv.common.Response
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    suspend fun signIn(): Flow<Response<Boolean>>

    suspend fun signInAnonymously(): Flow<Response<Boolean>>

    fun getCurrentUser(): AccountFeatureEntity?

    suspend fun createUser(uid: String): Flow<Response<Boolean>>

    suspend fun getPrivacyPolicy(): Flow<Response<String>>

}