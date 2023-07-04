package com.andreikslpv.data.service

import com.andreikslpv.data.dto.sets.ResultSets
import retrofit2.Call
import retrofit2.http.GET

interface SetsService {
    @GET("v1/sets?type=duel_deck")
    fun getSets(): Call<ResultSets>
}