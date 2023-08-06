package com.andreikslpv.data.cards

import androidx.paging.PagingData
import com.andreikslpv.data.cards.entities.CardDataModel
import kotlinx.coroutines.flow.Flow

interface CardsDataRepository {

    fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardDataModel>>

    fun changeApiAvailability(newStatus: Boolean)

}