package com.andreikslpv.domain_cards.repositories

import com.andreikslpv.domain.entities.CardEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface CardsExternalRepository {

    fun getCurrentUserUid(): String?

    fun getCollection(): MutableStateFlow<List<String>>

    fun addToUsersCollection(uid: String, cardId: String)

    fun removeFromUsersCollection(uid: String, cardId: String)

    fun getHistory(): MutableStateFlow<List<CardEntity>>

    fun setHistory(uid: String, newHistory: List<CardEntity>)

}