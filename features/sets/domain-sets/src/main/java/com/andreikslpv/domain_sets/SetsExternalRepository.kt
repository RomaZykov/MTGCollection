package com.andreikslpv.domain_sets

import kotlinx.coroutines.flow.Flow

interface SetsExternalRepository {

    fun getStartedTypeOfSet(): String

    suspend fun isNeedToUpdateTypesOfSet(): Flow<Int>

    fun getDefaultMatchValue(): Int

    fun setVersionForTypesOfSet(newVersion: Int)
}