package com.andreikslpv.data.sets.datasource

import kotlinx.coroutines.flow.MutableStateFlow

interface TypesDataSource {

    suspend fun getTypeNames(): MutableStateFlow<List<String>>

    fun getTypeCodeByName(name: String): String

}