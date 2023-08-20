package com.andreikslpv.cards.domain.usecase

import androidx.paging.PagingData
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.entities.CardsInitialData
import com.andreikslpv.cards.domain.repositories.CardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(initialData: CardsInitialData): Flow<PagingData<CardFeatureModel>> {
        return if (initialData.codeOfSet != null) {
            cardsRepository.getCardsInSet(initialData.codeOfSet)
        } else {
            cardsRepository.getCardsInCollection(initialData.ids)
        }

    }
}