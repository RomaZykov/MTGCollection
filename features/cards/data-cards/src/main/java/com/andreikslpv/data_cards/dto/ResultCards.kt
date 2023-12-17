package com.andreikslpv.data_cards.dto

import com.andreikslpv.data_cards.entities.CardApiEntity
import com.google.gson.annotations.SerializedName

data class ResultCards(
    @SerializedName("cards")
    val cards: List<CardApiEntity>
)