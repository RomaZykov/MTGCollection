package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import javax.inject.Inject

class AddCardToCollectionUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(card: CardFeatureModel) {
        val user = cardsRepository.getCurrentUser()
        if (user != null) {
            cardsRepository.addToCardsCollection(user.uid, card)
        }
    }
}