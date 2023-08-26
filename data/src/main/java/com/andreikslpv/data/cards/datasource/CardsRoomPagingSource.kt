package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.cards.dao.CardsDao
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.cards.mappers.CardsListRoomToDataModelMapper

class CardsRoomPagingSource (
    private val cardsDao: CardsDao,
    private val codeOfSet: String,
) : PagingSource<Int, CardDataModel>() {
    private val step = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CardDataModel> {
        return try {
            val sets = CardsListRoomToDataModelMapper.map(cardsDao.getCardsInSet(codeOfSet))

            LoadResult.Page(
                data = sets,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CardDataModel>): Int? {
        // Самый последний доступный индекс в списке
        val anchorPosition = state.anchorPosition ?: return null
        // преобразуем item index в page index
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page не имеет значения "текущее", поэтому вычисляем сами
        return page.prevKey?.plus(step) ?: page.nextKey?.minus(step)
    }
}