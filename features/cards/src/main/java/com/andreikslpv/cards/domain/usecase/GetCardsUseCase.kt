package com.andreikslpv.cards.domain.usecase

import androidx.paging.PagingData
import com.andreikslpv.common_impl.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    fun execute(codeOfSet: String?): Flow<PagingData<CardFeatureModel>> {
        return if (codeOfSet != null) {
            cardsRepository.getCardsInSet(codeOfSet)
        } else {
            val uid = cardsRepository.getCurrentUser()?.uid ?: ""
            cardsRepository.getCardsInCollection(uid)
        }
    }

}