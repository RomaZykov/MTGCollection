package com.andreikslpv.data.sets.service

import com.andreikslpv.data.sets.constants.ApiConstants
import com.andreikslpv.data.sets.dto.cards.ResultCards
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardsInSetService {
    @GET("{version}/{path}")
    fun getCards(
        @Path("version") version: String = ApiConstants.VERSION_API,
        @Path("path") path: String = ApiConstants.PATH_CARDS,
        @Query("set") set: String,
        @Query("page") page: String = ApiConstants.DEFAULT_PAGE,
        @Query("pageSize") pageSize: String = ApiConstants.DEFAULT_PAGE_SIZE,
    ): Call<ResultCards>
}