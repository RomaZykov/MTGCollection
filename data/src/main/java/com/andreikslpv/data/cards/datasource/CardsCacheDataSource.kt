package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.cards.entities.CardDataModel

interface CardsCacheDataSource {

    fun getCardsInSet(codeOfSet: String): PagingSource<Int, CardDataModel>

    fun saveCardsToDb(cards: List<CardDataModel>)

    fun deleteAllCards()

}