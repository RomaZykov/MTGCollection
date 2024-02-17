package com.andreikslpv.domain_settings.repositories

import com.andreikslpv.common.Response
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getStartedTypeOfSet(): String

    fun setStartedTypeOfSet(type: String)

    fun setVersionForTypesOfSet(newVersion: Int)

    fun refreshTypesOfSet()

    suspend fun getPrivacyPolicy(): Flow<Response<String>>

    suspend fun getRemoteVersionForTypesOfSet(): Flow<Int>

    fun getLocaleVersionForTypesOfSet(): Int

}