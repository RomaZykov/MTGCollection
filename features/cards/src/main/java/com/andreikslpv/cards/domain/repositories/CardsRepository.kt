package com.andreikslpv.cards.domain.repositories

import androidx.paging.PagingData
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardFeatureModel>>

    fun changeApiAvailability(newStatus: Boolean)

}