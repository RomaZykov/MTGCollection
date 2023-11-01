package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCardFromCollectionUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    fun execute(cardId: String): Flow<CardModel> {
        val uid = cardsExternalRepository.getCurrentUserUid()
        return if (uid != null) cardsRepository.getCardFromCollection(uid, cardId)
        else flowOf(CardModel())
    }
}