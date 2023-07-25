package com.andreikslpv.data.sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.data.sets.entities.SetRoomModel

interface SetsCacheDataSource {

    fun getSetsByType(type: String): PagingSource<Int, SetDataModel>

    fun getSetByCode(code: String): SetRoomModel

    fun saveSetsToDb(sets: List<SetDataModel>)

    fun saveSetToDb(set: SetDataModel)

    fun deleteAllSets()


}