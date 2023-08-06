package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.cards.entities.CardDataModel

interface CardsCacheDataSource {

    fun getCardsByIds(ids: List<String>): PagingSource<Int, CardDataModel>

    suspend fun saveCardsToDb(cards: List<CardDataModel>)

}