package com.andreikslpv.data_sets.datasource

import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import kotlinx.coroutines.flow.Flow

interface TypesDataSource {

    suspend fun getNamesOfTypes(): Flow<List<String>>

    suspend fun getAllTypes(): Flow<List<TypeOfSetEntity>>

    fun getTypeCodeByName(name: String): Flow<String>

    suspend fun updateTypesInDb(types: List<TypeOfSetEntity>)

}