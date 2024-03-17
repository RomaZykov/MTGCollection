package com.andreikslpv.data_cards.dtov2

import com.google.gson.annotations.SerializedName

/** API details [here](https://scryfall.com/docs/api/images) */
data class CardImageUris(
    /** Varies JPG
    * A rectangular crop of the card’s art only. Not guaranteed to be perfect for cards
    * with outlier designs or strange frame arrangements */
    @SerializedName("art_crop") val artCrop: String,
    /** 480 × 680 JPG
    A full card image with the rounded corners and the majority of the border cropped off.
    Designed for dated contexts where rounded images can’t be used. */
    @SerializedName("border_crop") val borderCrop: String,
    /** 672 × 936 JPG
    A large full card image */
    @SerializedName("large") val large: String,
    /** 488 × 680 JPG
    A medium-sized full card image */
    @SerializedName("normal") val normal: String,
    /** 745 × 1040 PNG
    A transparent, rounded full card PNG. This is the best image to use for videos or other high-quality content. */
    @SerializedName("png") val png: String,
    /** 146 × 204 JPG
    A small full card image. Designed for use as thumbnail or list icon. */
    @SerializedName("small") val small: String
)