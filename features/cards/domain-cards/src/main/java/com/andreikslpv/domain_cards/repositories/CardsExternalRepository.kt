package com.andreikslpv.domain_cards.repositories

import com.andreikslpv.domain.entities.CardPreviewEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface CardsExternalRepository {

    fun getCurrentUserUid(): String?

    fun getCollection(): MutableStateFlow<List<String>>

    suspend fun addToUsersCollection(uid: String, cardId: String)

    suspend fun removeFromUsersCollection(uid: String, cardId: String)

    fun getHistory(): MutableStateFlow<List<CardPreviewEntity>>

    suspend fun setHistory(uid: String, newHistory: List<CardPreviewEntity>)

}