package com.andreikslpv.data_sets

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.data.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data_sets.datasource.SetsApiDataSource
import com.andreikslpv.data_sets.datasource.SetsCacheDataSource
import com.andreikslpv.data_sets.datasource.TypesDataSource
import com.andreikslpv.domain_sets.SetsRepository
import com.andreikslpv.domain_sets.entities.SetModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetsRepositoryImpl @Inject constructor(
    private val apiDataSource: SetsApiDataSource,
    private val cacheDataSource: SetsCacheDataSource,
    private val typesDataSource: TypesDataSource,
) : SetsRepository {

    private var isApiAvailable = true

    override suspend fun getTypesOfSet() = typesDataSource.getTypeNames()

    override fun getSetsByType(type: String): Flow<PagingData<SetModel>> {
        val codeOfType = typesDataSource.getTypeCodeByName(type)
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                if (isApiAvailable) {
                    apiDataSource.getSetsByType(
                        codeOfType,
                        object : SetsApiCallback {
                            override fun onSuccess(items: List<SetModel>) {
                                if (isApiAvailable) cacheDataSource.saveSetsToDb(items)
                            }

                            override fun onFailure() {}
                        }
                    )
                } else {
                    // загружаем данные из кэша и меняем статус доступности апи на true,
                    // чтобы в следующий раз снова сначала была попытка получить данные из апи
                    isApiAvailable = true
                    cacheDataSource.getSetsByType(codeOfType)
                }
            }).flow
    }

    override fun changeApiAvailability(newStatus: Boolean) {
        isApiAvailable = newStatus
    }

}