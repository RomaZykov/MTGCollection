package com.andreikslpv.data.cards.dto

import com.google.gson.annotations.SerializedName

data class ForeignName(
    @SerializedName("flavor")
    val flavor: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("language")
    val language: String,
    @SerializedName("multiverseid")
    val multiverseid: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: String?
)