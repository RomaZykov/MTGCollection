package com.andreikslpv.data_cards.dto

import com.google.gson.annotations.SerializedName

data class CardPurchaseUris(
    @SerializedName("cardhoarder") val cardhoarder: String?,
    @SerializedName("cardmarket") val cardmarket: String?,
    @SerializedName("tcgplayer") val tcgplayer: String?,
)