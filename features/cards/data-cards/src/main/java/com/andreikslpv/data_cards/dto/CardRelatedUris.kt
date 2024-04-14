package com.andreikslpv.data_cards.dto

import com.google.gson.annotations.SerializedName

data class CardRelatedUris(
    @SerializedName("edhrec") val edhrec: String?,
    @SerializedName("gatherer") val gatherer: String?,
    @SerializedName("tcgplayer_infinite_articles") val tcgplayerInfiniteArticles: String?,
    @SerializedName("tcgplayer_infinite_decks") val tcgplayerInfiniteDecks: String?,
)