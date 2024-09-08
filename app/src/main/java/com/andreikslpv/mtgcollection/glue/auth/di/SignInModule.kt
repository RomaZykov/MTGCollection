package com.andreikslpv.mtgcollection.glue.auth.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.andreikslpv.mtgcollection.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SignInModule {

    @Provides
    fun provideGoogleIdOption(
        @ApplicationContext applicationContext: Context,
    ): GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(applicationContext.getString(R.string.default_web_client_id))
        .setAutoSelectEnabled(false)
        .build()

    @Provides
    fun provideCredentialRequest(
        googleIdOption: GetGoogleIdOption,
    ): GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    @Provides
    fun provideCredentialManager(
        @ApplicationContext applicationContext: Context,
    ): CredentialManager = CredentialManager.create(applicationContext)
}
