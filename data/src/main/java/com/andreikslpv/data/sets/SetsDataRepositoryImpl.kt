package com.andreikslpv.data.sets

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreikslpv.common.SettingsDataSource
import com.andreikslpv.data.constants.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data.sets.datasource.SetsApiDataSource
import com.andreikslpv.data.sets.datasource.SetsCacheDataSource
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.data.settings.ProjectSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SetsDataRepositoryImpl @Inject constructor(
    private val apiDataSource: SetsApiDataSource,
    private val cacheDataSource: SetsCacheDataSource,
    private val settingsDataSource: SettingsDataSource,
) : SetsDataRepository {

    private var isApiAvailable = true
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
            "masters",
            "memorabilia",
            "draft_innovation",
            "masterpiece",
        )
    )

    override suspend fun getTypesOfSet() = typesOfSet

    override fun getStartedTypeOfSet(): String {
        return try {
            (settingsDataSource.getSettingsValue(ProjectSettings.START_SETS_TYPE.value) as String)
        } catch (e: Exception) {
            (ProjectSettings.START_SETS_TYPE.value.defaultValue as String)
        }
    }

    override fun setStartedTypeOfSet(type: String) {
        settingsDataSource.putSettingsValue(ProjectSettings.START_SETS_TYPE.value, type)
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
                    apiDataSource.getSetsByType(
                        type,
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
                    cacheDataSource.getSetsByType(type)
                }
            }).flow
    }

    override fun changeApiAvailability(newStatus: Boolean) {
        isApiAvailable = newStatus
    }

}