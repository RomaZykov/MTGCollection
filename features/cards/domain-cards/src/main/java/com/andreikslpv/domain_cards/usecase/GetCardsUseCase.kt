package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain_cards.entities.CardFilters
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    operator fun invoke(filters: CardFilters?) = if (filters != null) {
        cardsRepository.getCardsInSet(filters)
    } else {
        cardsRepository.getCardsInCollection(
            cardsExternalRepository.getCurrentUserUid() ?: ""
        )
    }

}