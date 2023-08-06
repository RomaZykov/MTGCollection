package com.andreikslpv.data.sets

import androidx.paging.PagingData
import com.andreikslpv.data.sets.entities.SetDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SetsDataRepository {

    suspend fun getTypesOfSet(): MutableStateFlow<List<String>>

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

    fun getSetsByType(type: String): Flow<PagingData<SetDataModel>>

    fun changeApiAvailability(newStatus: Boolean)

}