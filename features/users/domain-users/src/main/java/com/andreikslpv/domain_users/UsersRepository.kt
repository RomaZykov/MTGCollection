package com.andreikslpv.domain_users

import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface UsersRepository {

    suspend fun createUserInDb(uid: String): Flow<Response<Boolean>>

    fun startObserveUserInDb(uid: String)

    fun stopObserveUserInDb()

    suspend fun deleteUserInDb(uid: String)

    fun getCollection(): MutableStateFlow<List<String>>

    suspend fun addToCollection(uid: String, cardId: String)

    suspend fun removeFromCollection(uid: String, cardId: String)

    suspend fun removeAllFromCollection(uid: String)

    fun getHistory(): MutableStateFlow<List<CardEntity>>

    suspend fun setHistory(uid: String, newHistory: List<CardEntity>)

}