package com.andreikslpv.data_cards.dto

import com.google.gson.annotations.SerializedName

data class ResultCards(
    @SerializedName("cards")
    val cards: List<Card>
)