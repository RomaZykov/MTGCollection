package com.andreikslpv.mtgcollection.glue.sets.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.andreikslpv.data.sets.SetsDataRepository
import com.andreikslpv.mtgcollection.glue.sets.SetLocalToFeatureModelMapper
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import com.andreikslpv.sets.domain.repositories.SetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterSetsRepository @Inject constructor(
    private val setsDataRepository: SetsDataRepository,
) : SetsRepository {
    override fun getTypesOfSet(): List<String> {
        return setsDataRepository.getTypesOfSet()
    }

    override fun getStartedTypeOfSet(): String {
        return setsDataRepository.getStartedTypeOfSet()
    }

    override fun setStartedTypeOfSet(type: String) {
        return setsDataRepository.setStartedTypeOfSet(type)
    }

    override fun getSetsByType(type: String): Flow<PagingData<SetFeatureModel>> {
        return setsDataRepository.getSetsByType(type).map {pagingData->
            pagingData.map {
                SetLocalToFeatureModelMapper.map(it)
            }
        }
    }
}