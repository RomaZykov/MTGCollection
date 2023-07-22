package com.andreikslpv.data.sets

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.data.ApiCallback
import com.andreikslpv.data.sets.constants.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data.sets.datasource.SetsPagingSource
import com.andreikslpv.data.sets.dto.cards.ResultCards
import com.andreikslpv.data.sets.entities.SetLocalModel
import com.andreikslpv.data.sets.service.SetsService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class SetsDataRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : SetsDataRepository {
    private var isApiAvailable = true
    private var startedType = "core"

    override fun getTypesOfSet(): List<String> {
        return listOf(
            "core",
            "expansion",
            "reprint",
            "box",
            "un",
            "from_the_vault",
            "premium_deck",
            "duel_deck",
            "starter",
            "commander",
            "planechase",
            "archenemy",
            "promo",
            "vanguard",
            "masters"
        )
    }

    override fun getStartedTypeOfSet(): String {
        return startedType
    }

    override fun setStartedTypeOfSet(type: String) {
        startedType = type
    }

    override fun getSetsByType(type: String): Flow<PagingData<SetLocalModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                SetsPagingSource(
                    retrofit.create(SetsService::class.java),
                    type,
                    object : ApiCallback {
                        override fun onSuccess(items: List<*>) {
                            println("AAA onSuccess $items")
//                            if (isApiAvailable) {
//                                cacheDataSource.putCategoryToCache(
//                                    apiDataSource.getApiType(),
//                                    category,
//                                    films,
//                                    currentIndex,
//                                )
//                            }
                        }

                        override fun onFailure() {
                        }
                    })
//                else {
//                    // загружаем данные из кэша и меняем статус доступности апи на true,
//                    // чтобы в следующий раз в режиме авто снова сначала была попытка получить данные из апи
//                    isApiAvailable = true
//                    cacheDataSource.getFilmsByCategoryPagingSource(
//                        apiDataSource.getApiType(),
//                        category
//                    )
//                }
            }).flow
    }

    override fun getCardsInSet(codeOfSet: String): ResultCards {
        TODO("Not yet implemented")
    }
}