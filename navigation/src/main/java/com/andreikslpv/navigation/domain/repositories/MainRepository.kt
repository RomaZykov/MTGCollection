package com.andreikslpv.navigation.domain.repositories

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAuthState() : Flow<Boolean>

    fun startObserveUser()
}