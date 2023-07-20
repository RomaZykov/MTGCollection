package com.andreikslpv.data.sets.dto.cards

data class ForeignName(
    val flavor: String?,
    val imageUrl: String,
    val language: String,
    val multiverseid: Int,
    val name: String,
    val text: String,
    val type: String
)