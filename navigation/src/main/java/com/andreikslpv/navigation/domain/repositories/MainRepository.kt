package com.andreikslpv.navigation.domain.repositories

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getCurrentUserUid(): String?

    fun getAuthState() : Flow<Boolean>

    fun startObserveUserInDb(uid: String)

    fun stopObserveUserInDb()

    fun sendErrorToCrashlytics(error: Throwable)

    suspend fun updateSets()

}