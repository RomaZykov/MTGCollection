package com.andreikslpv.domain_auth.repositories

import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardPreviewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface AuthExternalRepository {

    suspend fun createUser(uid: String): Flow<Response<Boolean>>

    suspend fun deleteUserInDb(uid: String)

    suspend fun removeAllFromCollection(uid: String)

    fun getCollection(): MutableStateFlow<List<String>>

    fun getHistory(): MutableStateFlow<List<CardPreviewEntity>>

    suspend fun addToUsersCollection(uid: String, cardId: String)

    suspend fun removeFromUsersCollection(uid: String, cardId: String)

    suspend fun getPrivacyPolicy(): Flow<Response<String>>

}