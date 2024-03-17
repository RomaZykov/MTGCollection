package com.andreikslpv.data_cards

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.andreikslpv.data.ApiConstants.DEFAULT_PAGE
import com.andreikslpv.data_cards.services.CardsApi
import com.andreikslpv.datasource_room_cards.CardPreviewRoomEntity
import com.andreikslpv.datasource_room_cards.CardsDao
import com.andreikslpv.domain.entities.CardLanguageV2
import com.andreikslpv.domain_cards.entities.SortsType
import com.andreikslpv.domain_cards.entities.SortsTypeDir
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@OptIn(ExperimentalPagingApi::class)
class CardsRemoteMediator @AssistedInject constructor(
    private val cardsDao: CardsDao,
    private val cardsApi: CardsApi,
    @Assisted private val codeOfSet: String,
    @Assisted private val lang: CardLanguageV2,
    @Assisted private val sortsType: SortsType,
    @Assisted private val sortsTypeDir: SortsTypeDir,
) : RemoteMediator<Int, CardPreviewRoomEntity>() {

    private var pageIndex = DEFAULT_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CardPreviewRoomEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        return try {
            // Если требуемый язык не английский, запрашиваем все доступные языки
            val includeMultilingual = lang != CardLanguageV2.ENGLISH
            val cardsResult = cardsApi.getCardsInSet(
                includeMultilingual = includeMultilingual,
                order = sortsType.typeApi,
                dir = sortsTypeDir.dirApi,
                page = pageIndex,
                q = "set:$codeOfSet",
            )
            // Пробуем получить карты на требуемом языке
            val cards = if (includeMultilingual) {
                cardsResult.cardData
                    .filter { it.lang == lang.cardLang }
                    .map { CardPreviewRoomEntity(it) }
                    .toMutableList()
            } else mutableListOf()
            // Если карт на требуемом языке нет,то берем карты на английском
            if (cards.isEmpty()) cards.addAll(
                cardsResult.cardData
                    .filter { it.lang == CardLanguageV2.ENGLISH.cardLang }
                    .map { CardPreviewRoomEntity(it) }
            )

            if (loadType == LoadType.REFRESH) cardsDao.refresh(codeOfSet, cards)
            else cardsDao.save(cards)
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
        fun create(
            codeOfSet: String,
            lang: CardLanguageV2,
            sortsType: SortsType,
            sortsTypeDir: SortsTypeDir
        ): CardsRemoteMediator
    }
}