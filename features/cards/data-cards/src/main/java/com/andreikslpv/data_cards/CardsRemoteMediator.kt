package com.andreikslpv.data_cards

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.andreikslpv.data.ApiConstants.DEFAULT_PAGE
import com.andreikslpv.data_cards.services.CardsApi
import com.andreikslpv.datasource_room_cards.CardRoomEntity
import com.andreikslpv.datasource_room_cards.CardsDao
import com.andreikslpv.domain.entities.CardLanguage
import com.andreikslpv.domain_cards.entities.CardFilters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.Locale

@OptIn(ExperimentalPagingApi::class)
class CardsRemoteMediator @AssistedInject constructor(
    private val cardsDao: CardsDao,
    private val cardsApi: CardsApi,
    @Assisted private val filters: CardFilters,
) : RemoteMediator<Int, CardRoomEntity>() {

    private var pageIndex = DEFAULT_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CardRoomEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        return try {
            val codeOfSet = filters.codeOfSet.lowercase(Locale.ROOT)
            // Если требуемый язык не английский, запрашиваем все доступные языки
            val includeMultilingual = filters.lang != CardLanguage.ENGLISH
            val cardsResult = cardsApi.getCardsInSet(
                includeMultilingual = includeMultilingual,
                order = filters.sortsType.typeApi,
                dir = filters.sortsTypeDir.dirApi,
                page = pageIndex,
                q = "set:$codeOfSet",
            )
            // Пробуем получить карты на требуемом языке
            val cards = if (includeMultilingual) {
                cardsResult.cardData
                    .filter { it.lang == filters.lang.cardLang }
                    .map { CardRoomEntity(it) }
                    .toMutableList()
            } else mutableListOf()
            // Если карт на требуемом языке нет,то берем карты на английском
            if (cards.isEmpty()) cards.addAll(
                cardsResult.cardData
                    .filter { it.lang == CardLanguage.ENGLISH.cardLang }
                    .map { CardRoomEntity(it) }
            )

            if (loadType == LoadType.REFRESH) {
                cardsDao.refresh(codeOfSet, cards)
                println("AndroidLogger refresh ${cards.size}")
            }
            else {
                cardsDao.save(cards)
                println("AndroidLogger save ${cards.size}")
            }
            MediatorResult.Success(endOfPaginationReached = !cardsResult.hasMore)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType) = when (loadType) {
        LoadType.REFRESH -> DEFAULT_PAGE
        LoadType.PREPEND -> null
        LoadType.APPEND -> ++pageIndex
    }

    @AssistedFactory
    interface Factory {
        fun create(filters: CardFilters): CardsRemoteMediator
    }
}