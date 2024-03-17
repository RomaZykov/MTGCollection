package com.andreikslpv.domain_cards.usecase

import com.andreikslpv.domain_cards.entities.CardFilters
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import java.util.Locale
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    operator fun invoke(filters: CardFilters) = cardsRepository.getCardsInSet(
            codeOfSet = filters.codeOfSet.lowercase(Locale.ROOT),
            lang = filters.lang,
            sortsType = filters.sortsType,
            sortsTypeDir = filters.sortsTypeDir,
        )


//    = if (codeOfSet != null) {
//        cardsRepository.getCardsInSet(codeOfSet)
//    } else {
//        val uid = cardsExternalRepository.getCurrentUserUid() ?: ""
//        cardsRepository.getCardsInCollection(uid)
//    }

}