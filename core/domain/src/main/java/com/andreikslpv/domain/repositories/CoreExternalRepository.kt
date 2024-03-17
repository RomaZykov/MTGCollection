package com.andreikslpv.domain.repositories

import com.andreikslpv.domain.entities.CardPreviewEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface CoreExternalRepository {

    fun getCurrentUserUid(): String?

    fun getCollection(): MutableStateFlow<List<String>>

    suspend fun addToUsersCollection(uid: String, cardId: String)

    suspend fun removeFromUsersCollection(uid: String, cardId: String)

    suspend fun addToCardsCollection(uid: String, card: CardPreviewEntity)

    suspend fun removeFromCardsCollection(uid: String, card: CardPreviewEntity)

}