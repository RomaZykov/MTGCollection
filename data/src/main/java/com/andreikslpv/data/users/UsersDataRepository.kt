package com.andreikslpv.data.users

import com.andreikslpv.common.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface UsersDataRepository {

    suspend fun createUserInDb(uid: String): Flow<Response<Boolean>>

    fun startObserveUser(uid: String)

    suspend fun deleteUserInDb(uid: String): Flow<Response<Boolean>>

    fun getAvailable(): MutableStateFlow<List<String>>

    fun addToAvailable(uid: String, cardId: String)

    fun removeFromAvailable(uid: String, cardId: String)

    fun removeAllFromAvailable(uid: String)

    fun getHistory(): MutableStateFlow<List<String>>

    fun setHistory(uid: String, newHistory: List<String>)

}