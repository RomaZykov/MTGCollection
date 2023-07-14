package com.andreikslpv.mtgcollection.glue.signin.di

import com.andreikslpv.mtgcollection.glue.signin.AdapterSignInRouter
import com.andreikslpv.sign_in.presentation.SignInRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignInRouterModule {

    @Binds
    fun bindSignInRouter(router: AdapterSignInRouter): SignInRouter

}