package com.andreikslpv.domain.entities

import java.io.Serializable

data class CardRelatedUrisEntity(
    val edhrec: String = "",
    val gatherer: String = "",
    val tcgplayerInfiniteArticles: String = "",
    val tcgplayerInfiniteDecks: String = "",
) : Serializable
