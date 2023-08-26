package com.andreikslpv.data.sets.dto

import com.google.gson.annotations.SerializedName

data class ResultSets(
    @SerializedName("sets")
    val sets: List<Set>
)