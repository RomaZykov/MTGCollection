package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import javax.inject.Inject

class TryToChangeCollectionStatusUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(card: com.andreikslpv.domain.entities.CardFeatureModel): Boolean {
        val user = cardsRepository.getCurrentUser()
        return if (user != null) {
            cardsRepository.getCollection().value.let { collection ->
                if (collection.contains(card.id)) {
                    cardsRepository.removeFromUsersCollection(user.uid, card.id)
                    cardsRepository.removeFromCardsCollection(user.uid, card)
                }
                else {
                    cardsRepository.addToUsersCollection(user.uid, card.id)
                    card.ownerUid = user.uid
                    cardsRepository.addToCardsCollection(user.uid, card)
                }
            }
            true
        } else {
            false
        }
    }
}