package com.andreikslpv.cards.domain.entities

data class AvailableCardFeatureModel(
    val language: String = "",
    var count: Int = 0,
    val foiled: Boolean = false,
    val condition: String = "",
)
