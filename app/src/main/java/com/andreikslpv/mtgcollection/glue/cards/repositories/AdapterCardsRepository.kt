package com.andreikslpv.mtgcollection.glue.cards.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.repositories.CardsRepository
import com.andreikslpv.data.cards.CardsDataRepository
import com.andreikslpv.mtgcollection.glue.cards.CardDataToFeatureModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterCardsRepository @Inject constructor(
    private val cardsDataRepository: CardsDataRepository,
) : CardsRepository {

    override fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardFeatureModel>> {
        return cardsDataRepository.getCardsInSet(codeOfSet).map { pagingData ->
            pagingData.map {
                CardDataToFeatureModelMapper.map(it)
            }
        }
    }

    override fun changeApiAvailability(newStatus: Boolean) {
        cardsDataRepository.changeApiAvailability(newStatus)
    }
}