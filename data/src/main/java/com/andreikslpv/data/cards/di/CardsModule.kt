package com.andreikslpv.data.cards.di

import com.andreikslpv.data.cards.CardsDataRepository
import com.andreikslpv.data.cards.CardsDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CardsModule {

    @Provides
    @Singleton
    fun providesCardsDataRepository(cardsDataRepository: CardsDataRepositoryImpl): CardsDataRepository {
        return cardsDataRepository
    }
}