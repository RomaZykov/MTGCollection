package com.andreikslpv.data.cards.datasource

import com.andreikslpv.data.cards.dao.CardsDao
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.cards.mappers.CardsListDataToRoomModelMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardsRoomDataSource @Inject constructor(
    private val cardsDao: CardsDao,
): CardsCacheDataSource {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getCardsInSet(codeOfSet: String) = CardsRoomPagingSource(cardsDao, codeOfSet)

    override fun saveCardsToDb(cards: List<CardDataModel>) {
        scope.launch {
            try{
                cardsDao.insertCards(CardsListDataToRoomModelMapper.map(cards))
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