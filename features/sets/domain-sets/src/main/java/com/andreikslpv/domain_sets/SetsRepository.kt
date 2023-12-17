package com.andreikslpv.domain_sets

import androidx.paging.PagingData
import com.andreikslpv.domain_sets.entities.SetEntity
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import kotlinx.coroutines.flow.Flow

interface SetsRepository {

    suspend fun getNamesOfAllTypesOfSet(): Flow<List<String>>

    suspend fun getAllTypes(): Flow<List<TypeOfSetEntity>>

    fun getTypeCodeByName(nameOfType: String): Flow<String>

    fun getSetsByType(codeOfType: String): Flow<PagingData<SetEntity>>

    suspend fun updateTypesInDb(types: List<TypeOfSetEntity>)

    suspend fun getTypesOfSetInRemoteDb(): Flow<List<TypeOfSetEntity>>

}