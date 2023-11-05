package com.andreikslpv.data_cards.dto

import com.google.gson.annotations.SerializedName

data class Ruling(
    @SerializedName("date")
    val date: String,
    @SerializedName("text")
    val text: String
)