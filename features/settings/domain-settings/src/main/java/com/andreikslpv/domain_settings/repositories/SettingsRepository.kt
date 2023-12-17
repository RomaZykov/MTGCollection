package com.andreikslpv.domain_settings.repositories

import com.andreikslpv.common.Response
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

    /**
     * Retrieves versions of set types from local and remote storage and compares them.
     * @return the version from the remote storage if the versions do not match, or [getDefaultMatchValue] if they match
     */
    suspend fun isNeedToUpdateTypesOfSet(): Flow<Int>

    fun setVersionForTypesOfSet(newVersion: Int)

    fun refreshTypesOfSet()

    fun getDefaultMatchValue(): Int

    suspend fun getPrivacyPolicy(): Flow<Response<String>>
}