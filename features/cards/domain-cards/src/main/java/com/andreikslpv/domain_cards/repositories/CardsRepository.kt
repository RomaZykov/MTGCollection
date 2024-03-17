package com.andreikslpv.domain_cards.repositories

import androidx.paging.PagingData
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.CardLanguageV2
import com.andreikslpv.domain.entities.CardPreviewEntity
import com.andreikslpv.domain_cards.entities.SortsType
import com.andreikslpv.domain_cards.entities.SortsTypeDir
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getCardsInSet(
        codeOfSet: String,
        lang: CardLanguageV2,
        sortsType: SortsType,
        sortsTypeDir: SortsTypeDir
    ): Flow<PagingData<CardPreviewEntity>>

    fun getCardsInCollection(uid: String): Flow<PagingData<CardPreviewEntity>>

    suspend fun addToCardsCollection(uid: String, card: CardPreviewEntity)

    suspend fun removeFromCardsCollection(uid: String, card: CardPreviewEntity)

    fun getCardFromCollection(uid: String, cardId: String): Flow<CardEntity>

    suspend fun removeAllFromCollection(uid: String)

}