package com.andreikslpv.data_sets.dto


import com.andreikslpv.domain_sets.entities.SetEntity
import com.google.gson.annotations.SerializedName

/** API details [here](https://scryfall.com/docs/api/sets) */
data class SetData(
    /** The unique code for this set on Arena, which may differ from the regular code. */
    @SerializedName ("arena_code") override val arenaCode: String?,
    /** The block or group name code for this set, if any. */
    @SerializedName ("block") override val block: String?,
    /** The block code for this set, if any. */
    @SerializedName ("block_code") override val blockCode: String?,
    /** The number of cards in this set. */
    @SerializedName ("card_count") override val cardCount: Int,
    /** The unique three to five-letter code for this set. */
    @SerializedName ("code") override val code: String,
    /** True if this set was only released in a video game. */
    @SerializedName ("digital") override val digital: Boolean,
    /** True if this set contains only foil cards. */
    @SerializedName ("foil_only") override val foilOnly: Boolean,
    /** A URI to an SVG file for this set’s icon on Scryfall’s CDN.
     * Hotlinking this image isn’t recommended, because it may change slightly over time.
     * You should download it and use it locally for your particular user interface needs. */
    @SerializedName ("icon_svg_uri") override val iconSvgUri: String,
    /** A unique ID for this set on Scryfall that will not change. */
    @SerializedName ("id") override val id: String,
    /** The unique code for this set on MTGO, which may differ from the regular code. */
    @SerializedName ("mtgo_code") override val mtgoCode: String?,
    /** The English name of the set. */
    @SerializedName ("name") override val name: String,
    /** True if this set contains only nonfoil cards. */
    @SerializedName ("nonfoil_only") override val nonfoilOnly: Boolean,
    /** A content type for this object, always set. */
    @SerializedName ("object") override val objectX: String,
    /** The set code for the parent set, if any. promo and token sets often have a parent set. */
    @SerializedName ("parent_set_code") override val parentSetCode: String?,
    /** The denominator for the set’s printed collector numbers. */
    @SerializedName ("printed_size") override val printedSize: Int?,
    /** The date the set was released or the first card was printed in the set (in GMT-8 Pacific time). */
    @SerializedName ("released_at") override val releasedAt: String?,
    /** A link to this set’s permapage on Scryfall’s website. */
    @SerializedName ("scryfall_uri") override val scryfallUri: String,
    /** A Scryfall API URI that you can request to begin paginating over the cards in this set. */
    @SerializedName ("search_uri") override val searchUri: String,
    /** A computer-readable classification for this set. */
    @SerializedName ("set_type") override val setType: String,
    /** This set’s ID on TCGplayer’s API, also known as the groupId. */
    @SerializedName ("tcgplayer_id") override val tcgplayerId: Int?,
    /** A link to this set object on Scryfall’s API. */
    @SerializedName ("uri") override val uri: String,
) : SetEntity