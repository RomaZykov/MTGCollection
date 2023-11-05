package com.andreikslpv.domain_sets

import androidx.paging.PagingData
import com.andreikslpv.domain_sets.entities.SetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SetsRepository {

    suspend fun getTypesOfSet(): MutableStateFlow<List<String>>

    fun getSetsByType(type: String): Flow<PagingData<SetModel>>

    fun changeApiAvailability(newStatus: Boolean)

}