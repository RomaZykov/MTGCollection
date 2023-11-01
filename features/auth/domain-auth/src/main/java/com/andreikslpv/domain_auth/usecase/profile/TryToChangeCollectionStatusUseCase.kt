package com.andreikslpv.domain_auth.usecase.profile

import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_auth.repositories.AuthExternalRepository
import com.andreikslpv.domain_auth.repositories.AuthRepository
import javax.inject.Inject

class TryToChangeCollectionStatusUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authExternalRepository: AuthExternalRepository,
) {

    fun execute(card: CardModel): Boolean {
        val user = authRepository.getCurrentUser()
        return if (user != null) {
            authExternalRepository.getCollection().value.let { collection ->
                if (collection.contains(card.id)) {
                    authExternalRepository.removeFromUsersCollection(user.uid, card.id)
                    authExternalRepository.removeFromCardsCollection(user.uid, card)
                }
                else {
                    authExternalRepository.addToUsersCollection(user.uid, card.id)
                    authExternalRepository.addToCardsCollection(user.uid, card)
                }
            }
            true
        } else {
            false
        }
    }
}