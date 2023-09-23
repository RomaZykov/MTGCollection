package com.andreikslpv.data.sets.datasource

import com.andreikslpv.data.sets.SetsApiCallback
import com.andreikslpv.data.sets.services.SetsService
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