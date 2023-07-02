package com.andreikslpv.mtgcollection.dto.sets

data class Set(
    val block: String,
    val booster: List<Any>,
    val code: String,
    val name: String,
    val onlineOnly: Boolean,
    val releaseDate: String,
    val type: String
)