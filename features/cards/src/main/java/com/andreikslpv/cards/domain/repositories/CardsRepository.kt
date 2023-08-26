package com.andreikslpv.cards.domain.repositories

import androidx.paging.PagingData
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.common_impl.entities.AccountFeatureEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface CardsRepository {

    fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardFeatureModel>>

    fun getCardsInCollection(uid: String): Flow<PagingData<CardFeatureModel>>

    fun changeApiAvailability(newStatus: Boolean)

    fun getCurrentUser(): AccountFeatureEntity?

    fun getCollection(): MutableStateFlow<List<String>>

    fun addToUsersCollection(uid: String, cardId: String)

    fun removeFromUsersCollection(uid: String, cardId: String)

    fun addToCardsCollection(uid: String, card: CardFeatureModel)

    fun removeFromCardsCollection(uid: String, card: CardFeatureModel)

}