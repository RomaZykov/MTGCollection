package com.andreikslpv.cards.domain.usecase

import androidx.paging.PagingData
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
) {

    suspend fun execute(codeOfSet: String?): Flow<PagingData<CardFeatureModel>> {
        return if (codeOfSet != null) {
            println("AAA GetCardsInSetUseCase if $codeOfSet")
            cardsRepository.getCardsInSet(codeOfSet)
        } else {
            println("AAA GetCardsInSetUseCase else")
            cardsRepository.getCollection().collect{
                println("AAA GetCardsInSetUseCase $it")
                cardsRepository.getCardsInCollection(it)
            }

        }

    }
}