package com.andreikslpv.data_sets.datasource

import com.andreikslpv.datasource_room_sets.SetRoomEntity
import com.andreikslpv.datasource_room_sets.SetsDao
import com.andreikslpv.domain_sets.entities.SetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetsRoomDataSource @Inject constructor(
    private val setsDao: SetsDao,
) : SetsDataSource {

    override fun getSetsByType(type: String) = setsDao.getSetsByType(type).flowOn(Dispatchers.IO)

    override suspend fun saveSets(sets: List<SetEntity>) = withContext(Dispatchers.IO) {
        setsDao.refresh(sets.map { SetRoomEntity(it) })
    }
}