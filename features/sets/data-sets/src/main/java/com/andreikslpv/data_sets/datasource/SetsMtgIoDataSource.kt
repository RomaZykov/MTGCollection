package com.andreikslpv.data_sets.datasource

import com.andreikslpv.data_sets.services.SetsService
import com.andreikslpv.data_sets.SetsApiCallback
import retrofit2.Retrofit
import javax.inject.Inject

class SetsMtgIoDataSource @Inject constructor(
    private val retrofit: Retrofit,
) : SetsApiDataSource {

    override fun getSetsByType(type: String, callback: SetsApiCallback) = SetsApiPagingSource(
        retrofit.create(SetsService::class.java),
        type,
        callback
    )

}