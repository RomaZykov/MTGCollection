package com.andreikslpv.data.sets.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.sets.SetsApiCallback
import com.andreikslpv.data.constants.ApiConstants.DEFAULT_PAGE
import com.andreikslpv.data.constants.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.data.sets.mappers.SetsListDtoToDataModelMapper
import com.andreikslpv.data.sets.services.SetsService
import retrofit2.HttpException

class SetsApiPagingSource(
    private val service: SetsService,
    private val type: String,
    private val callback: SetsApiCallback,
) : PagingSource<Int, SetDataModel>() {
    private val step = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SetDataModel> {
        try {
            val pageNumber = params.key ?: DEFAULT_PAGE
            val response = service.getSets(
                type = type,
                page = pageNumber
            )

            return if (response.isSuccessful) {
                val sets = SetsListDtoToDataModelMapper.map(response.body()!!.sets)
                callback.onSuccess(sets)
                LoadResult.Page(
                    data = sets,
                    prevKey = if (pageNumber > DEFAULT_PAGE) pageNumber - step else null,
                    nextKey = if (response.body()!!.sets.size >= DEFAULT_PAGE_SIZE) pageNumber + step else null
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
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