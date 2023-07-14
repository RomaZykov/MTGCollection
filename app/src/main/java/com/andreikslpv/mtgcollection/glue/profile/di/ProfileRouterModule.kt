package com.andreikslpv.mtgcollection.glue.profile.di

import com.andreikslpv.profile.presentation.ProfileRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import com.andreikslpv.mtgcollection.glue.profile.AdapterProfileRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface ProfileRouterModule {

    @Binds
    fun bindProfileRouter(
        profileRouter: AdapterProfileRouter,
    ): ProfileRouter

}