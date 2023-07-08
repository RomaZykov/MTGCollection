package com.andreikslpv.data.auth.di

import com.andreikslpv.data.auth.AuthDataRepository
import com.andreikslpv.data.auth.AuthFirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthDataRepository(authDataRepository: AuthFirebaseRepository): AuthDataRepository{
        return authDataRepository
    }
}