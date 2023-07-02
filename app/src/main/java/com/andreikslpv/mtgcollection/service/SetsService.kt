package com.andreikslpv.mtgcollection.service

import com.andreikslpv.mtgcollection.dto.sets.ResultSets
import retrofit2.Call
import retrofit2.http.GET

interface SetsService {
    @GET("v1/sets?type=duel_deck")
    fun getSets(): Call<ResultSets>
}