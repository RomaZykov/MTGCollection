package com.andreikslpv.data_settings.remote

import com.andreikslpv.common.Response
import com.andreikslpv.domain_settings.entities.SettingPrivacyPolicy
import com.andreikslpv.domain_settings.entities.SettingVersionSetsType
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRemoteConfigDataSource @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
) : RemoteSettingsDataSource {

    override suspend fun getVersionSetsType(): Int {
        remoteConfig.fetchAndActivate().await().also {
            return remoteConfig.getLong(SettingVersionSetsType().key).toInt()
        }
    }

    override suspend fun getPrivacyPolicy() = flow {
        try {
            emit(Response.Loading)
            remoteConfig.fetchAndActivate().await().also {
                emit(Response.Success(remoteConfig.getString(SettingPrivacyPolicy().key)))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

}