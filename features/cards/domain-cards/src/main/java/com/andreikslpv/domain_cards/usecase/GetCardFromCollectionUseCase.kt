package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCardFromCollectionUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    operator fun invoke(cardId: String): Flow<CardEntity> {
        cardsExternalRepository.getCurrentUserUid()?.let {
            return cardsRepository.getCardFromCollection(it, cardId)
        } ?: return flowOf(CardUiEntity(null))
    }
}