package com.andreikslpv.data_cards.dto

import com.google.gson.annotations.SerializedName

data class CardLegalities(
    @SerializedName("alchemy")
    val alchemy: String,
    @SerializedName("brawl")
    val brawl: String,
    @SerializedName("commander")
    val commander: String,
    @SerializedName("duel")
    val duel: String,
    @SerializedName("explorer")
    val explorer: String,
    @SerializedName("future")
    val future: String,
    @SerializedName("gladiator")
    val gladiator: String,
    @SerializedName("historic")
    val historic: String,
    @SerializedName("legacy")
    val legacy: String,
    @SerializedName("modern")
    val modern: String,
    @SerializedName("oathbreaker")
    val oathbreaker: String,
    @SerializedName("oldschool")
    val oldschool: String,
    @SerializedName("pauper")
    val pauper: String,
    @SerializedName("paupercommander")
    val paupercommander: String,
    @SerializedName("penny")
    val penny: String,
    @SerializedName("pioneer")
    val pioneer: String,
    @SerializedName("predh")
    val predh: String,
    @SerializedName("premodern")
    val premodern: String,
    @SerializedName("standard")
    val standard: String,
    @SerializedName("standardbrawl")
    val standardbrawl: String,
    @SerializedName("timeless")
    val timeless: String,
    @SerializedName("vintage")
    val vintage: String
)