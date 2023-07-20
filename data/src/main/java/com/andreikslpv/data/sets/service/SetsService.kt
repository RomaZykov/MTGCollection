package com.andreikslpv.data.sets.service

import com.andreikslpv.data.sets.dto.sets.ResultSets
import com.andreikslpv.data.sets.constants.ApiConstants.DEFAULT_PAGE
import com.andreikslpv.data.sets.constants.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data.sets.constants.ApiConstants.PATH_SETS
import com.andreikslpv.data.sets.constants.ApiConstants.VERSION_API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SetsService {
    @GET("{version}/{path}")
    fun getSets(
        @Path("version") version: String = VERSION_API,
        @Path("path") path: String = PATH_SETS,
        @Query("type") type: String,
        @Query("page") page: String = DEFAULT_PAGE,
        @Query("pageSize") pageSize: String = DEFAULT_PAGE_SIZE,
    ): Call<ResultSets>
}