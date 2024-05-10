package com.andreikslpv.data_sets.datasource

import com.andreikslpv.domain_sets.entities.SetEntity
import kotlinx.coroutines.flow.Flow

interface SetsDataSource {

    fun getSetsByType(type: String): Flow<List<SetEntity>>

    suspend fun saveSets(sets: List<SetEntity>)

}