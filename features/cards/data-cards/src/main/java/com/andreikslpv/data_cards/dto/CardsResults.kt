package com.andreikslpv.data_cards.dto

import com.google.gson.annotations.SerializedName

/** API details [here](https://scryfall.com/docs/api/lists) */
data class CardsResults(
    /** An array of the requested objects, in a specific order. */
    @SerializedName("data") val cardData: List<CardData>,
    /** True if this List is paginated and there is a page beyond the current page. */
    @SerializedName("has_more") val hasMore: Boolean,
    /** If there is a page beyond the current page, this field will contain a full API URI to that page.
     * You may submit a HTTP GET request to that URI to continue paginating forward on this List. */
    @SerializedName("next_page") val nextPage: String?,
    /** A content type for this object, always list */
    @SerializedName("object") val objectX: String,
    /** If this is a list of Card objects, this field will contain the total number of cards found across all pages. */
    @SerializedName("total_cards") val totalCards: Int?,
    /** An array of human-readable warnings issued when generating this list, as strings.
     * Warnings are non-fatal issues that the API discovered with your input. In general,
     * they indicate that the List will not contain the all of the information you requested.
     * You should fix the warnings and re-submit your request. */
    @SerializedName("warnings") val warnings: List<String>?,
)