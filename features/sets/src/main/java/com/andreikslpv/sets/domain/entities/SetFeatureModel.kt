package com.andreikslpv.sets.domain.entities

data class SetFeatureModel(
    val block: String,
    val code: String,
    val name: String,
    val onlineOnly: Boolean,
    val releaseDate: String,
    val type: String
)
