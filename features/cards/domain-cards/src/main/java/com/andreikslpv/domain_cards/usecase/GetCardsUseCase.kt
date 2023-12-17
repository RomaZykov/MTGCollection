package com.andreikslpv.domain_cards.usecase

import androidx.paging.PagingData
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain_cards.repositories.CardsExternalRepository
import com.andreikslpv.domain_cards.repositories.CardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val cardsExternalRepository: CardsExternalRepository,
) {

    fun execute(codeOfSet: String?): Flow<PagingData<CardEntity>> {
        return if (codeOfSet != null) {
            cardsRepository.getCardsInSet(codeOfSet)
        } else {
            val uid = cardsExternalRepository.getCurrentUserUid() ?: ""
            cardsRepository.getCardsInCollection(uid)
        }
    }

}