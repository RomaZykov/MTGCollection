package com.andreikslpv.data.cards

import androidx.paging.PagingData
import com.andreikslpv.common.Response
import com.andreikslpv.data.cards.entities.CardDataModel
import kotlinx.coroutines.flow.Flow

interface CardsDataRepository {

    fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardDataModel>>

    fun getCardsInCollection(uid: String): Flow<PagingData<CardDataModel>>

    fun changeApiAvailability(newStatus: Boolean)

    fun addToCollection(uid: String, card: CardDataModel)

    fun removeFromCollection(uid: String, card: CardDataModel)

    fun getCardFromCollection(uid: String, cardId: String): Flow<CardDataModel?>

    fun removeAllFromCollection(uid: String): Flow<Response<Boolean>>

}