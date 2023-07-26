package com.andreikslpv.sets.domain.repositories

import androidx.paging.PagingData
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SetsRepository {

    suspend fun getTypesOfSet(): MutableStateFlow<List<String>>

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

    fun getSetsByType(type: String): Flow<PagingData<SetFeatureModel>>

    fun changeApiAvailability(newStatus: Boolean)

}