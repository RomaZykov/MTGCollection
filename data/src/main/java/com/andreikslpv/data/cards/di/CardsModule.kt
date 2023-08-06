package com.andreikslpv.data.cards.di

import com.andreikslpv.data.cards.CardsDataRepository
import com.andreikslpv.data.cards.CardsDataRepositoryImpl
import com.andreikslpv.data.cards.datasource.CardsCacheDataSource
import com.andreikslpv.data.cards.datasource.CardsFirebaseDataSource
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

    @Provides
    @Singleton
    fun providesCardsCacheDataSource(cardsCacheDataSource: CardsFirebaseDataSource): CardsCacheDataSource {
        return cardsCacheDataSource
    }
}