package com.andreikslpv.data_users

import com.andreikslpv.domain_users.UsersRepository
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
    fun providesUsersDataRepository(usersRepository: UsersRepositoryImpl): UsersRepository {
        return usersRepository
    }
}