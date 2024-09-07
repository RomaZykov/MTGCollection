package com.andreikslpv.domain.entities

import java.io.Serializable

data class CardPurchaseUrisEntity(
    val cardhoarder: String = "",
    val cardmarket: String = "",
    val tcgplayer: String = "",
) : Serializable
