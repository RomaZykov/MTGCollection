package com.andreikslpv.data_sets.datasource

import com.andreikslpv.data.db.dao.SetsDao
import com.andreikslpv.data_sets.mappers.SetsListDataToRoomModelMapper
import com.andreikslpv.domain_sets.entities.SetModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetsRoomDataSource @Inject constructor(
    private val setsDao: SetsDao,
) : SetsCacheDataSource {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getSetsByType(type: String) = SetsRoomPagingSource(setsDao, type)

    override fun saveSetsToDb(sets: List<SetModel>) {
        scope.launch {
            try{
                setsDao.insertSets(SetsListDataToRoomModelMapper.map(sets))
            } catch (_: Exception) {
            }

        }
    }

    override fun deleteAllSets() {
        scope.launch {
            try{
                setsDao.deleteAllSets()
            } catch (_: Exception) {
            }
        }
    }
}