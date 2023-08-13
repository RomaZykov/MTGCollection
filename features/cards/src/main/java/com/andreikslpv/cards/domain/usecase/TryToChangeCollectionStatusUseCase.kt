package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.cards.domain.repositories.CardsRepository
import javax.inject.Inject

class TryToChangeCollectionStatusUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(cardId: String): Boolean {
        val user = cardsRepository.getCurrentUser()
        return if (user != null) {
            cardsRepository.getCollection().value.let { collection ->
                if (collection.contains(cardId))
                    cardsRepository.removeFromCollection(user.uid, cardId)
                else
                    cardsRepository.addToCollection(user.uid, cardId)
            }
            true
        } else {
            false
        }
    }
}