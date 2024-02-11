package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import javax.inject.Inject

class AddCardToCollectionUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    suspend operator fun invoke(card: CardEntity) {
        val uid = cardsExternalRepository.getCurrentUserUid()
        if (uid != null) cardsRepository.addToCardsCollection(uid, card)
    }
}