package com.andreikslpv.data.sets.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.sets.dao.SetsDao
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.data.sets.mappers.SetsListRoomToDataModelMapper

class SetsRoomPagingSource (
    private val setsDao: SetsDao,
    private val type: String,
) : PagingSource<Int, SetDataModel>() {
    private val step = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SetDataModel> {
        return try {
            val sets = SetsListRoomToDataModelMapper.map(setsDao.getSetsByType(type))

            LoadResult.Page(
                data = sets,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SetDataModel>): Int? {
        // Самый последний доступный индекс в списке
        val anchorPosition = state.anchorPosition ?: return null
        // преобразуем item index в page index
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page не имеет значения "текущее", поэтому вычисляем сами
        return page.prevKey?.plus(step) ?: page.nextKey?.minus(step)
    }
}