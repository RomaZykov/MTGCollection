package com.andreikslpv.data_sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.domain_sets.entities.SetModel

interface SetsCacheDataSource {

    fun getSetsByType(type: String): PagingSource<Int, SetModel>

    fun saveSetsToDb(sets: List<SetModel>)

    fun deleteAllSets()


}