package com.andreikslpv.data_sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.data_sets.SetsApiCallback
import com.andreikslpv.domain_sets.entities.SetModel

interface SetsApiDataSource {

    fun getSetsByType(type: String, callback: SetsApiCallback): PagingSource<*, SetModel>

}