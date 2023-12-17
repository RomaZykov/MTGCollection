package com.andreikslpv.data_cards.services

import com.andreikslpv.data.ApiConstants
import com.andreikslpv.data_cards.dto.ResultCards
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API for fetching Cards.
 */

interface CardsApi {

    /**
     * Note that orderBy and ASC/DESC order should be the same as in the database query!
     */

    @GET("{version}/{path}")
    suspend fun getCards(
        @Path("version") version: String = ApiConstants.VERSION_API,
        @Path("path") path: String = ApiConstants.PATH_CARDS,
        @Query("set") set: String,
        @Query("page") page: Int = ApiConstants.DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = ApiConstants.DEFAULT_PAGE_SIZE,
    ): ResultCards
}