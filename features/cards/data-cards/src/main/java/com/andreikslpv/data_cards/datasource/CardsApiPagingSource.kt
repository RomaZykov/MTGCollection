package com.andreikslpv.data_cards.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andreikslpv.data.constants.ApiConstants
import com.andreikslpv.data_cards.CardsApiCallback
import com.andreikslpv.data_cards.mappers.CardsListDtoToFeatureModelMapper
import com.andreikslpv.data_cards.services.CardsInSetService
import com.andreikslpv.domain.entities.CardModel
import retrofit2.HttpException

class CardsApiPagingSource(
    private val service: CardsInSetService,
    private val set: String,
    private val callback: CardsApiCallback,
) : PagingSource<Int, CardModel>() {
    private val step = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CardModel> {
        try {
            val pageNumber = params.key ?: ApiConstants.DEFAULT_PAGE
            val response = service.getCards(
                set = set,
                page = pageNumber
            )

            return if (response.isSuccessful) {
                val cards = CardsListDtoToFeatureModelMapper.map(response.body()!!.cards)
                callback.onSuccess(cards)
                LoadResult.Page(
                    data = cards,
                    prevKey = if (pageNumber > ApiConstants.DEFAULT_PAGE) pageNumber - step else null,
                    nextKey = if (response.body()!!.cards.size >= ApiConstants.DEFAULT_PAGE_SIZE) pageNumber + step else null
                )
            } else {
                LoadResult.Error(HttpException(response))
//                TODO: Check for type of errors
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
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