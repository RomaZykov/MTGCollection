package com.andreikslpv.mtgcollection.glue.auth.di

import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.mtgcollection.glue.auth.AdapterAuthExternalRepository
import com.andreikslpv.mtgcollection.glue.auth.AdapterAuthRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AuthAppModule {

    @Binds
    @Singleton
    fun bindAuthRouter(router: AdapterAuthRouter): AuthRouter

    @Binds
    @Singleton
    fun bindAuthExternalRepository(authExternalRepository: AdapterAuthExternalRepository): AuthExternalRepository

}