package com.andreikslpv.data_cards.dto

import com.google.gson.annotations.SerializedName

data class CardPrices(
    @SerializedName("eur")
    val eur: String?,
    @SerializedName("eur_etched")
    val eurEtched: String?,
    @SerializedName("eur_foil")
    val eurFoil: String?,
    @SerializedName("tix")
    val tix: String?,
    @SerializedName("usd")
    val usd: String?,
    @SerializedName("usd_etched")
    val usdEtched: String?,
    @SerializedName("usd_foil")
    val usdFoil: String?,
)