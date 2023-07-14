package com.andreikslpv.mtgcollection.glue.cards.di

import com.andreikslpv.cards.presentation.CardsRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface CardsRouterModule {

    @Binds
    fun bindCardsRouter(
        cardsRouter: CardsRouter,
    ): CardsRouter
}