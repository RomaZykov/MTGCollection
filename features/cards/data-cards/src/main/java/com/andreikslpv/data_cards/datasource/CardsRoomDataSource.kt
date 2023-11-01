package com.andreikslpv.data_cards.datasource

import com.andreikslpv.data.db.dao.CardsDao
import com.andreikslpv.data_cards.mappers.CardsListFeatureToRoomModelMapper
import com.andreikslpv.domain.entities.CardModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardsRoomDataSource @Inject constructor(
    private val cardsDao: CardsDao,
): CardsCacheDataSource {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getCardsInSet(codeOfSet: String) = CardsRoomPagingSource(cardsDao, codeOfSet)

    override fun saveCardsToDb(cards: List<CardModel>) {
        scope.launch {
            try{
                cardsDao.insertCards(CardsListFeatureToRoomModelMapper.map(cards))
            } catch (_: Exception) {
            }
        }
    }

    override fun deleteAllCards() {
        scope.launch {
            try{
                cardsDao.deleteAllCards()
            } catch (_: Exception) {
            }
        }
    }

}