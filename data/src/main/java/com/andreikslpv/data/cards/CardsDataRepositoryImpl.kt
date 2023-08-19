package com.andreikslpv.data.cards

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.data.cards.datasource.CardsApiPagingSource
import com.andreikslpv.data.cards.datasource.CardsCacheDataSource
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.cards.services.CardsInSetService
import com.andreikslpv.data.constants.ApiConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class CardsDataRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val cacheDataSource: CardsCacheDataSource,
) : CardsDataRepository {
    private var isApiAvailable = true

    override fun getCardsInSet(codeOfSet: String): Flow<PagingData<CardDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                CardsApiPagingSource(
                    retrofit.create(CardsInSetService::class.java),
                    codeOfSet,
                    object : CardsApiCallback {
                        override fun onSuccess(items: List<CardDataModel>) {
                            if (isApiAvailable) CoroutineScope(Dispatchers.IO).launch {
                                cacheDataSource.saveCardsToDb(items)
                            }
                        }

                        override fun onFailure() {}
                    })
            }
//                else {
//                    // загружаем данные из кэша и меняем статус доступности апи на true,
//                    // чтобы в следующий раз снова сначала была попытка получить данные из апи
//                    isApiAvailable = true
//                    cacheDataSource.getSetsByType(type)
//                }
//            }
        ).flow
    }

    override fun getCardsInCollection(ids: List<String>): Flow<PagingData<CardDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = { cacheDataSource.getCardsByIds(ids) }).flow
    }

    override fun changeApiAvailability(newStatus: Boolean) {
        isApiAvailable = newStatus
    }
}