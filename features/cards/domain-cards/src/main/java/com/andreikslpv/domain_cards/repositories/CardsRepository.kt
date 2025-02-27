package com.andreikslpv.domain_cards.repositories

import androidx.paging.PagingData
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_cards.entities.CardFilters
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardsInSet(filters: CardFilters): Flow<PagingData<CardEntity>>

    fun getCardsInCollection(uid: String): Flow<PagingData<CardEntity>>

    suspend fun addToCardsCollection(uid: String, card: CardEntity)

    suspend fun removeFromCardsCollection(uid: String, card: CardEntity)

    fun getCardFromCollection(uid: String, cardId: String): Flow<CardEntity>

    suspend fun removeAllFromCollection(uid: String)

}