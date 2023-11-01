package com.andreikslpv.data_cards.datasource

import androidx.paging.PagingSource
import com.andreikslpv.domain.entities.CardModel

interface CardsCacheDataSource {

    fun getCardsInSet(codeOfSet: String): PagingSource<Int, CardModel>

    fun saveCardsToDb(cards: List<CardModel>)

    fun deleteAllCards()

}