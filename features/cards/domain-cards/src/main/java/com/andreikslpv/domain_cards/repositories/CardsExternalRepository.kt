package com.andreikslpv.domain_cards.repositories

import com.andreikslpv.domain.entities.CardEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface CardsExternalRepository {

    fun getCurrentUserUid(): String?

    fun getCollection(): MutableStateFlow<List<String>>

    suspend fun addToUsersCollection(uid: String, cardId: String)

    suspend fun removeFromUsersCollection(uid: String, cardId: String)

    fun getHistory(): MutableStateFlow<List<CardEntity>>

    suspend fun setHistory(uid: String, newHistory: List<CardEntity>)

}