package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.cards.entities.CardDataModel
import com.google.firebase.firestore.QuerySnapshot

interface CardsCacheDataSource {

    fun getCardsByIds(ids: List<String>): PagingSource<QuerySnapshot, CardDataModel>

    suspend fun saveCardsToDb(cards: List<CardDataModel>)

}