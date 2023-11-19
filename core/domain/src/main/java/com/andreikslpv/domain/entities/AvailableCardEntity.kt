package com.andreikslpv.domain.entities

import java.io.Serializable

data class AvailableCardEntity(
    val language: String = "",
    var count: Int = 0,
    val foiled: Boolean = false,
    val condition: String = "",
): Serializable
