package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.cards.entities.CardDataModel
import com.google.firebase.firestore.DocumentSnapshot

interface CardsCacheDataSource {

    fun getCardsByIds(ids: List<String>): PagingSource<DocumentSnapshot, CardDataModel>

    suspend fun saveCardsToDb(cards: List<CardDataModel>)

}