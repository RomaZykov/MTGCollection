package com.andreikslpv.data.cards.dto

import com.google.gson.annotations.SerializedName

data class CardDetail(
    @SerializedName("card")
    val card: Card
)