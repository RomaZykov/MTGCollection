package com.andreikslpv.data.sets.services

import com.andreikslpv.data.constants.ApiConstants.DEFAULT_PAGE
import com.andreikslpv.data.constants.ApiConstants.DEFAULT_PAGE_SIZE
import com.andreikslpv.data.constants.ApiConstants.PATH_SETS
import com.andreikslpv.data.constants.ApiConstants.VERSION_API
import com.andreikslpv.data.sets.dto.ResultSets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SetsService {
    @GET("{version}/{path}")
    suspend fun getSets(
        @Path("version") version: String = VERSION_API,
        @Path("path") path: String = PATH_SETS,
        @Query("type") type: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<ResultSets>
}