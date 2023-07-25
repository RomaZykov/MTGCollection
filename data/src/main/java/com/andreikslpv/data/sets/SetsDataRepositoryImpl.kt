package com.andreikslpv.data.sets

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.data.ApiCallback
import com.andreikslpv.data.sets.constants.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data.sets.datasource.SetsApiPagingSource
import com.andreikslpv.data.sets.datasource.SetsCacheDataSource
import com.andreikslpv.data.sets.dto.cards.ResultCards
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.data.sets.service.SetsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Retrofit
import javax.inject.Inject

class SetsDataRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val cacheDataSource: SetsCacheDataSource,
) : SetsDataRepository {
    private var isApiAvailable = true
    private var startedType = "core"
    private val typesOfSet = MutableStateFlow(
        listOf(
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
    )

    override suspend fun getTypesOfSet() = typesOfSet

    override fun getStartedTypeOfSet(): String {
        return startedType
    }

    override fun setStartedTypeOfSet(type: String) {
        startedType = type
    }

    override fun getSetsByType(type: String): Flow<PagingData<SetDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                if (isApiAvailable) {
                    SetsApiPagingSource(
                        retrofit.create(SetsService::class.java),
                        type,
                        object : ApiCallback {
                            override fun onSuccess(items: List<SetDataModel>) {
                                if (isApiAvailable) cacheDataSource.saveSetsToDb(items)
                            }

                            override fun onFailure() {}
                        })
                }
                else {
                    // загружаем данные из кэша и меняем статус доступности апи на true,
                    // чтобы в следующий раз снова сначала была попытка получить данные из апи
                    isApiAvailable = true
                    cacheDataSource.getSetsByType(type)
                }
            }).flow
    }

    override fun changeApiAvailability(newStatus: Boolean) {
        isApiAvailable = newStatus
    }

    override fun getCardsInSet(codeOfSet: String): ResultCards {
        TODO("Not yet implemented")
    }
}