package com.andreikslpv.mtgcollection.glue.auth.di

import com.andreikslpv.domain_auth.repositories.AuthRouter
import com.andreikslpv.mtgcollection.glue.auth.AdapterAuthRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AuthRouterModule {

    @Binds
    fun bindAuthRouter(router: AdapterAuthRouter): AuthRouter

}