package com.andreikslpv.sets.domain.repositories

import androidx.paging.PagingData
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import kotlinx.coroutines.flow.Flow

interface SetsRepository {

    fun getTypesOfSet(): List<String>

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

    fun getSetsByType(type: String): Flow<PagingData<SetFeatureModel>>

}