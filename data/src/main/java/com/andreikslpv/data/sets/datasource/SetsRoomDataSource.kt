package com.andreikslpv.data.sets.datasource

import com.andreikslpv.data.sets.dao.SetsDao
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.data.sets.entities.SetRoomModel
import com.andreikslpv.data.sets.mappers.SetsListDataToRoomModelMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetsRoomDataSource @Inject constructor(
    private val setsDao: SetsDao,
) : SetsCacheDataSource {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getSetsByType(type: String) = SetsRoomPagingSource(setsDao, type)

    override fun getSetByCode(code: String): SetRoomModel {
        TODO("Not yet implemented")
    }

    override fun saveSetsToDb(sets: List<SetDataModel>) {
        scope.launch {
            try{
                setsDao.insertSets(SetsListDataToRoomModelMapper.map(sets))
            } catch (_: Exception) {
            }

        }
    }

    override fun saveSetToDb(set: SetDataModel) {
        TODO("Not yet implemented")
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