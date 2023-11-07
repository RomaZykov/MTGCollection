package com.andreikslpv.domain.entities

data class AvailableCardEntity(
    val language: String = "",
    var count: Int = 0,
    val foiled: Boolean = false,
    val condition: String = "",
)
