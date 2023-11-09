package com.andreikslpv.domain_sets.entities

data class SetEntity(
    val block: String = "",
    val code: String = "",
    val name: String = "",
    val onlineOnly: Boolean = false,
    val releaseDate: String = "",
    val type: String = "",
    val symbolUrl: String = "",
    val cardCount: Int = 0,
)
