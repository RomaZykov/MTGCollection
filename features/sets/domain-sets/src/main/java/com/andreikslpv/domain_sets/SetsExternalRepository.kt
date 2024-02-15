package com.andreikslpv.domain_sets

import kotlinx.coroutines.flow.Flow

interface SetsExternalRepository {

    fun getStartedTypeOfSet(): String

    fun setVersionForTypesOfSet(newVersion: Int)

    fun refreshTypesOfSet()

    suspend fun getRemoteVersionForTypesOfSet(): Flow<Int>

    fun getLocaleVersionForTypesOfSet(): Int

}