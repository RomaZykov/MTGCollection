package com.andreikslpv.data.sets.dto.sets

import com.google.gson.annotations.SerializedName

data class ResultSets(
    @SerializedName("sets")
    val sets: List<Set>
)