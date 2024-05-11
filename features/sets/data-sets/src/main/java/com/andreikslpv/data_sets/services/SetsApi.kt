package com.andreikslpv.data_sets.services

import com.andreikslpv.data_sets.dto.SetsResults
import retrofit2.http.GET

/** API for fetching Sets. */
interface SetsApi {

    @GET("sets")
    suspend fun getAllSets(): SetsResults

}