package com.andreikslpv.data.cards.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.cards.CardsApiCallback
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.cards.mappers.CardsListDtoToDataModelMapper
import com.andreikslpv.data.cards.services.CardsInSetService
import com.andreikslpv.data.constants.ApiConstants
import retrofit2.HttpException

class CardsApiPagingSource(
    private val service: CardsInSetService,
    private val set: String,
    private val callback: CardsApiCallback,
) : PagingSource<Int, CardDataModel>() {
    private val step = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CardDataModel> {
        try {
            val pageNumber = params.key ?: ApiConstants.DEFAULT_PAGE
            val response = service.getCards(
                set = set,
                page = pageNumber
            )

            return if (response.isSuccessful) {
                val cards = CardsListDtoToDataModelMapper.map(response.body()!!.cards)
                callback.onSuccess(cards)
                println("AAA CardsApiPagingSource 1")
                LoadResult.Page(
                    data = cards,
                    prevKey = if (pageNumber > ApiConstants.DEFAULT_PAGE) pageNumber - step else null,
                    nextKey = if (response.body()!!.cards.size >= ApiConstants.DEFAULT_PAGE_SIZE) pageNumber + step else null
                )
            } else {
                println("AAA CardsApiPagingSource 2")
                LoadResult.Error(HttpException(response))
//                TODO: Check for type of errors
            }
        } catch (e: HttpException) {
            println("AAA CardsApiPagingSource 3")
            return LoadResult.Error(e)
        } catch (e: Exception) {
            println("AAA CardsApiPagingSource 4 ${e.message}")
            return LoadResult.Error(e)
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