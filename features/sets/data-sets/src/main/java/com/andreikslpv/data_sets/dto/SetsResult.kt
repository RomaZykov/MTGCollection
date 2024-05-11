package com.andreikslpv.data_sets.dto


import com.google.gson.annotations.SerializedName

data class SetsResults(
    @SerializedName ("data")
    val `data`: List<SetData>,
    @SerializedName ("has_more")
    val hasMore: Boolean,
    @SerializedName ("object")
    val objectX: String
)