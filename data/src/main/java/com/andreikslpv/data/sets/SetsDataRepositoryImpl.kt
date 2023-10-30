package com.andreikslpv.data.sets

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.data.constants.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data.sets.datasource.SetsApiDataSource
import com.andreikslpv.data.sets.datasource.SetsCacheDataSource
import com.andreikslpv.data.sets.datasource.TypesDataSource
import com.andreikslpv.data.sets.entities.SetDataModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetsDataRepositoryImpl @Inject constructor(
    private val apiDataSource: SetsApiDataSource,
    private val cacheDataSource: SetsCacheDataSource,

    private val typesDataSource: TypesDataSource,
) : SetsDataRepository {

    private var isApiAvailable = true

    override suspend fun getTypesOfSet() = typesDataSource.getTypeNames()

    override fun getStartedTypeOfSet(): String {
        TODO("Not yet implemented")
    }

    override fun setStartedTypeOfSet(type: String) {
        TODO("Not yet implemented")
    }


    override fun getSetsByType(nameOfType: String): Flow<PagingData<SetDataModel>> {
        val codeOfType = typesDataSource.getTypeCodeByName(nameOfType)
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
                            override fun onSuccess(items: List<SetDataModel>) {
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