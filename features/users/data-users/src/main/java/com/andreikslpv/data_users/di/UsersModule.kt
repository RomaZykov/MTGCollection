package com.andreikslpv.data_users.di

import com.andreikslpv.data_users.UsersRepositoryImpl
import com.andreikslpv.domain_users.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UsersModule {

    @Binds
    @Singleton
    fun bindUsersRepository(usersRepository: UsersRepositoryImpl): UsersRepository
}