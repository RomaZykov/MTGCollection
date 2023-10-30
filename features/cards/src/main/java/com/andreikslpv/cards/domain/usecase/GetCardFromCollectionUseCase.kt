package com.andreikslpv.cards.domain.usecase

import com.andreikslpv.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCardFromCollectionUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(cardId: String): Flow<com.andreikslpv.domain.entities.CardFeatureModel> {
        val user = cardsRepository.getCurrentUser()

        return if (user != null) cardsRepository.getCardFromCollection(user.uid, cardId)
        else flowOf(com.andreikslpv.domain.entities.CardFeatureModel())
    }
}