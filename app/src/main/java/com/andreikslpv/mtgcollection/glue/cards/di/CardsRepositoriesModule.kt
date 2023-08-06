package com.andreikslpv.mtgcollection.glue.cards.di

import com.andreikslpv.cards.domain.repositories.CardsRepository
import com.andreikslpv.mtgcollection.glue.cards.repositories.AdapterCardsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CardsRepositoriesModule {

    @Binds
    @Singleton
    fun bindCardsRepository(
        cardsRepository: AdapterCardsRepository
    ): CardsRepository
}