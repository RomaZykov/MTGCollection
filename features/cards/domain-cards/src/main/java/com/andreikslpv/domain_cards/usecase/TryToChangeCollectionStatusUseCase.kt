package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import javax.inject.Inject

class TryToChangeCollectionStatusUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    fun execute(card: CardEntity): Boolean {
        val uid = cardsExternalRepository.getCurrentUserUid()
        return if (uid != null) {
            cardsExternalRepository.getCollection().value.let { collection ->
                if (collection.contains(card.id)) {
                    cardsExternalRepository.removeFromUsersCollection(uid, card.id)
                    cardsRepository.removeFromCardsCollection(uid, card)
                }
                else {
                    cardsExternalRepository.addToUsersCollection(uid, card.id)
                    cardsRepository.addToCardsCollection(uid, card)
                }
            }
            true
        } else {
            false
        }
    }
}