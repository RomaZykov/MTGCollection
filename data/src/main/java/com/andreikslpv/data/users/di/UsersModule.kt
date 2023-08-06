package com.andreikslpv.data.users.di

import com.andreikslpv.data.users.UsersDataRepository
import com.andreikslpv.data.users.UsersDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsersModule {

    @Provides
    @Singleton
    fun providesUsersDataRepository(userRepository: UsersDataRepositoryImpl): UsersDataRepository {
        return userRepository
    }
}