package com.andreikslpv.data.cards.services

import com.andreikslpv.data.cards.dto.CardDetail
import retrofit2.Call
import retrofit2.http.GET

interface CardService {
    @GET("v1/cards/386616")
    fun getCard(): Call<CardDetail>
}