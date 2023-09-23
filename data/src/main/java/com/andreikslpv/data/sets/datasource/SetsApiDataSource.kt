package com.andreikslpv.data.sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data.sets.SetsApiCallback
import com.andreikslpv.data.sets.entities.SetDataModel

interface SetsApiDataSource {

    fun getSetsByType(type: String, callback: SetsApiCallback): PagingSource<*, SetDataModel>

}