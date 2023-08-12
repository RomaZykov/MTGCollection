package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.cards.domain.repositories.CardsRepository
import javax.inject.Inject

class TryToChangeAvailableStatusUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(cardId: String): Boolean {
        val user = cardsRepository.getCurrentUser()
        return if (user != null) {
            cardsRepository.getAvailable().value.let { available ->
                if (available.contains(cardId))
                    cardsRepository.removeFromAvailable(user.uid, cardId)
                else
                    cardsRepository.addToAvailable(user.uid, cardId)
            }
            true
        } else {
            false
        }
    }
}