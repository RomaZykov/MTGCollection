package com.andreikslpv.domain.usecase

import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.repositories.CoreExternalRepository
import javax.inject.Inject

class TryToChangeCollectionStatusUseCase @Inject constructor(
    private val coreExternalRepository: CoreExternalRepository,
) {

    suspend operator fun invoke(card: CardEntity) {
        coreExternalRepository.getCurrentUserUid()?.let {
            coreExternalRepository.getCollection().value.let { collection ->
                if (collection.contains(card.id)) {
                    coreExternalRepository.removeFromUsersCollection(it, card.id)
                    coreExternalRepository.removeFromCardsCollection(it, card)
                } else {
                    coreExternalRepository.addToUsersCollection(it, card.id)
                    coreExternalRepository.addToCardsCollection(it, card)
                }
            }
        }
    }
}