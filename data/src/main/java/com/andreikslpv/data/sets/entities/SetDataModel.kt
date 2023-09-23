package com.andreikslpv.data.sets.entities

data class SetDataModel(
    val block: String = "",
    val code: String = "",
    val name: String = "",
    val onlineOnly: Boolean = false,
    val releaseDate: String = "",
    val type: String = "",
    val symbolUrl: String = "",
    val cardCount: Int = 0,
)