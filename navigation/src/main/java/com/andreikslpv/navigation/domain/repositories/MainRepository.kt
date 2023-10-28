package com.andreikslpv.navigation.domain.repositories

import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getCurrentUser(): AccountFeatureEntity?

    fun getAuthState() : Flow<Boolean>

    fun startObserveUserInDb(uid: String)

}