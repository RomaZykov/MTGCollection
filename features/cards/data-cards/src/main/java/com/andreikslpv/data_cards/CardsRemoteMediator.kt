package com.andreikslpv.data_cards

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.andreikslpv.data_cards.services.CardsApi
import com.andreikslpv.datasource_room_cards.CardRoomEntity
import com.andreikslpv.datasource_room_cards.CardsDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@OptIn(ExperimentalPagingApi::class)
class CardsRemoteMediator @AssistedInject constructor(
    private val cardsDao: CardsDao,
    private val cardsApi: CardsApi,
    @Assisted private val codeOfSet: String,
) : RemoteMediator<Int, CardRoomEntity>() {

    private var pageIndex = START_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CardRoomEntity>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val cards = cardsApi.getCards(
                set = codeOfSet,
                page = pageIndex
            ).cards.map { CardRoomEntity(it) }

            if (loadType == LoadType.REFRESH) cardsDao.refresh(codeOfSet, cards)
            else cardsDao.save(cards)
            MediatorResult.Success(endOfPaginationReached = cards.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> START_PAGE
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    companion object {
        const val START_PAGE = 0
    }

    @AssistedFactory
    interface Factory {
        fun create(codeOfSet: String): CardsRemoteMediator
    }
}