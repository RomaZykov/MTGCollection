package com.andreikslpv.profile.domain.usecase

import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class TryToChangeCollectionStatusUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {

    fun execute(card: CardFeatureModel): Boolean {
        val user = profileRepository.getCurrentUser()
        return if (user != null) {
            profileRepository.getCollection().value.let { collection ->
                if (collection.contains(card.id)) {
                    profileRepository.removeFromUsersCollection(user.uid, card.id)
                    profileRepository.removeFromCardsCollection(user.uid, card)
                }
                else {
                    profileRepository.addToUsersCollection(user.uid, card.id)
                    card.ownerUid = user.uid
                    profileRepository.addToCardsCollection(user.uid, card)
                }
            }
            true
        } else {
            false
        }
    }
}