package com.andreikslpv.data_cards.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.db.dao.CardsDao
import com.andreikslpv.data_cards.mappers.CardsListRoomToFeatureModelMapper
import com.andreikslpv.domain.entities.CardModel

class CardsRoomPagingSource (
    private val cardsDao: CardsDao,
    private val codeOfSet: String,
) : PagingSource<Int, CardModel>() {
    private val step = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CardModel> {
        return try {
            val sets = CardsListRoomToFeatureModelMapper.map(cardsDao.getCardsInSet(codeOfSet))

            LoadResult.Page(
                data = sets,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CardModel>): Int? {
        // Самый последний доступный индекс в списке
        val anchorPosition = state.anchorPosition ?: return null
        // преобразуем item index в page index
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page не имеет значения "текущее", поэтому вычисляем сами
        return page.prevKey?.plus(step) ?: page.nextKey?.minus(step)
    }
}