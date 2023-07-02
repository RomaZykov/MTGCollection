package com.andreikslpv.mtgcollection.service

import com.andreikslpv.mtgcollection.dto.cards.CardDetail
import retrofit2.Call
import retrofit2.http.GET

interface CardService {
    @GET("v1/cards/386616")
    fun getCard(): Call<CardDetail>
}