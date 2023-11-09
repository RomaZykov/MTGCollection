package com.andreikslpv.data_sets.datasource

import androidx.paging.PagingSource
import com.andreikslpv.domain_sets.entities.SetEntity

interface SetsDataSource {

    fun getSetsByType(type: String): PagingSource<*, SetEntity>

}