package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    operator fun invoke(codeOfSet: String?) = if (codeOfSet != null) {
        cardsRepository.getCardsInSet(codeOfSet)
    } else {
        val uid = cardsExternalRepository.getCurrentUserUid() ?: ""
        cardsRepository.getCardsInCollection(uid)
    }

}