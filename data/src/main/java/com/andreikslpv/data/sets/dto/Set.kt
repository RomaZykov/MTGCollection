package com.andreikslpv.data.sets.dto

import com.google.gson.annotations.SerializedName

data class Set(
    @SerializedName("block")
    val block: String?,
    @SerializedName("booster")
    val booster: List<Any>,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("onlineOnly")
    val onlineOnly: Boolean,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("type")
    val type: String
)