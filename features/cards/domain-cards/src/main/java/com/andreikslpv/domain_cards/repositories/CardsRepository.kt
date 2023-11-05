package com.andreikslpv.domain_cards.repositories

import androidx.paging.PagingData
import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardModel
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardModel>>

    fun getCardsInCollection(uid: String): Flow<PagingData<CardModel>>

    fun changeApiAvailability(newStatus: Boolean)

    fun addToCardsCollection(uid: String, card: CardModel)

    fun removeFromCardsCollection(uid: String, card: CardModel)

    fun getCardFromCollection(uid: String, cardId: String): Flow<CardModel>

    fun removeAllFromCollection(uid: String): Flow<Response<Boolean>>

}