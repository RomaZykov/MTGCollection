package com.andreikslpv.data.users

import com.andreikslpv.common.Response
import com.andreikslpv.data.cards.entities.CardDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface UsersDataRepository {

    suspend fun createUserInDb(uid: String): Flow<Response<Boolean>>

    fun startObserveUserInDb(uid: String)

    suspend fun deleteUserInDb(uid: String): Flow<Response<Boolean>>

    fun getCollection(): MutableStateFlow<List<String>>

    fun addToCollection(uid: String, cardId: String)

    fun removeFromCollection(uid: String, cardId: String)

    fun removeAllFromCollection(uid: String)

    fun getHistory(): MutableStateFlow<List<CardDataModel>>

    fun setHistory(uid: String, newHistory: List<CardDataModel>)

}