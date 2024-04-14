package com.andreikslpv.data.di

import android.content.Context
import androidx.room.Room
import com.andreikslpv.data.db.AppDatabase
import com.andreikslpv.data.db.AppDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext applicationContext: Context
    ) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideTypeOfSetDao(appDatabase: AppDatabase) = appDatabase.typeOfSetDao()

    @Singleton
    @Provides
    fun provideCardsDao(appDatabase: AppDatabase) = appDatabase.cardsDao()

}