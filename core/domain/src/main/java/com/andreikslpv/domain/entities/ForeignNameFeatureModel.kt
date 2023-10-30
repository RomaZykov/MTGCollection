package com.andreikslpv.domain.entities

data class ForeignNameFeatureModel(
    val flavor: String?,
    val imageUrl: String,
    val language: String,
    val multiverseid: Int,
    val name: String,
    val text: String,
    val type: String,
)
