package com.andreikslpv.data_cards.services

import com.andreikslpv.data.ApiConstants.DEFAULT_FORMAT
import com.andreikslpv.data.ApiConstants.DEFAULT_INCLUDE_EXTRAS
import com.andreikslpv.data.ApiConstants.DEFAULT_INCLUDE_MULTILINGUAL
import com.andreikslpv.data.ApiConstants.DEFAULT_INCLUDE_VARIATIONS
import com.andreikslpv.data.ApiConstants.DEFAULT_UNIQUE
import com.andreikslpv.data_cards.dtov2.CardsResults
import com.andreikslpv.domain_cards.entities.SortsType
import com.andreikslpv.domain_cards.entities.SortsTypeDir
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API for fetching Cards.
 */

interface CardsApi {

    /**
     * API for app version 1.1+
     * API details [here](https://scryfall.com/docs/api/cards/search)
     */
    @GET("cards/search")
    suspend fun getCardsInSet(
        @Query("format") format: String = DEFAULT_FORMAT,
        @Query("include_extras") includeExtras: Boolean = DEFAULT_INCLUDE_EXTRAS,
        @Query("include_multilingual") includeMultilingual: Boolean = DEFAULT_INCLUDE_MULTILINGUAL,
        @Query("include_variations") includeVariations: Boolean = DEFAULT_INCLUDE_VARIATIONS,
        @Query("order") order: String = SortsType.SET.typeApi,
        @Query("dir") dir: String = SortsTypeDir.ASC.dirApi,
        @Query("page") page: Int,
        @Query("q") q: String,
        @Query("unique") unique: String = DEFAULT_UNIQUE,
    ): CardsResults

}