package com.andreikslpv.mtgcollection.service

import com.andreikslpv.mtgcollection.dto.cards.ResultCards
import retrofit2.Call
import retrofit2.http.GET

interface CardsInSetService {
    @GET("v1/cards?set=ktk&page=3")
    fun getCards(): Call<ResultCards>
}