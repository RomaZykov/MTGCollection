package com.andreikslpv.domain.usecase

import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.repositories.CoreExternalRepository
import javax.inject.Inject

class TryToChangeCollectionStatusUseCase @Inject constructor(
    private val coreExternalRepository: CoreExternalRepository,
) {

    suspend operator fun invoke(card: CardEntity): Boolean {
        val userUid = coreExternalRepository.getCurrentUserUid()
        return if (userUid != null) {
            coreExternalRepository.getCollection().value.let { collection ->
                if (collection.contains(card.id)) {
                    coreExternalRepository.removeFromUsersCollection(userUid, card.id)
                    coreExternalRepository.removeFromCardsCollection(userUid, card)
                }
                else {
                    coreExternalRepository.addToUsersCollection(userUid, card.id)
                    coreExternalRepository.addToCardsCollection(userUid, card)
                }
            }
            true
        } else {
            false
        }
    }
}