package com.andreikslpv.data_cards.dto

import com.andreikslpv.domain.entities.AvailableCardEntity
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.CardPurchaseUrisEntity
import com.andreikslpv.domain.entities.CardRelatedUrisEntity
import com.google.gson.annotations.SerializedName

/** API details [here](https://scryfall.com/docs/api/cards) */
data class CardData(
    /** The name of the illustrator of this card. Newly spoiled cards may not have this field yet. */
    @SerializedName("artist") override val artist: String?,
    /** The IDs of the artists that illustrated this card. Newly spoiled cards may not have this field yet. */
    @SerializedName("artist_ids") val artistIds: List<String>?,
    /** Whether this card is found in boosters. */
    @SerializedName("booster") val booster: Boolean,
    /** This card’s border color: black, white, borderless, silver, or gold. */
    @SerializedName("border_color") val borderColor: String,
    /** The Scryfall ID for the card back design present on this card. */
    @SerializedName("card_back_id") override val cardBackId: String,
    /** This card’s ID on Cardmarket’s API, also known as the idProduct. */
    @SerializedName("cardmarket_id") val cardmarketId: Int?,
    /** The card’s mana value. Note that some funny cards have fractional mana costs. */
    @SerializedName("cmc") override val cmc: Double,
    /** This card’s collector number. Note that collector numbers can contain non-numeric characters,
     * such as letters or ★. */
    @SerializedName("collector_number") override val collectorNumber: String,
    /** This card’s color identity. */
    @SerializedName("color_identity") override val colorIdentity: List<String>,
    /** This card’s colors, if the overall card has colors defined by the rules. Otherwise the colors will be on the card_faces objects, see below. */
    @SerializedName("colors") override val colors: List<String>?,
    /** True if this card was only released in a video game. */
    @SerializedName("digital") val digital: Boolean,
    /** This card’s overall rank/popularity on EDHREC. Not all cards are ranked. */
    @SerializedName("edhrec_rank") override val edhrecRank: Int?,
    /** An array of computer-readable flags that indicate if this card can come in foil, nonfoil, or etched finishes. */
    @SerializedName("finishes") override val finishes: List<String>,
    /** The flavor text printed on this face, if any. */
    @SerializedName("flavor_text") val flavorText: String?,
    /**  */
    @SerializedName("foil") override val foil: Boolean?,
    /** This card’s frame layout. */
    @SerializedName("frame") val frame: String,
    /** True if this card’s artwork is larger than normal. */
    @SerializedName("full_art") val fullArt: Boolean,
    /** A list of games that this card print is available in, paper, arena, and/or mtgo. */
    @SerializedName("games") val games: List<String>,
    /** True if this card’s imagery is high resolution. */
    @SerializedName("highres_image") override val highresImage: Boolean,
    /** A unique ID for this card in Scryfall’s database. */
    @SerializedName("id") override val id: String,
    /** A unique identifier for the card artwork that remains consistent across reprints.
     * Newly spoiled cards may not have this field yet. */
    @SerializedName("illustration_id") val illustrationId: String?,
    /** A computer-readable indicator for the state of this card’s image, one of:
     *
     * missing На карте нет изображения, или изображение обрабатывается. Это значение должно
     * быть временным только для очень новых карт.
     *
     * placeholder У Scryfall нет изображения этой карты, но мы знаем, что она существует,
     * и тем временем мы загрузили заполнитель. Это значение чаще всего встречается на локализованных картах.
     *
     * lowres Изображение карты некачественное, либо потому, что оно было просто испорчено,
     * либо у нас пока нет лучшей фотографии для него.
     *
     * highres_scan На этой карте есть изображение от сканера в полном разрешении. Четкое и глянцевое!
     */
    @SerializedName("image_status") override val imageStatus: String,
    /** An object listing available imagery for this card. See the Card Imagery article for more information. */
    @SerializedName("image_uris") val cardImageUris: CardImageUris?,
    /** An array of keywords that this card uses, such as 'Flying' and 'Cumulative upkeep'. */
    @SerializedName("keywords") val keywords: List<String>,
    /** A language code for this printing. */
    @SerializedName("lang") override val lang: String,
    /** A code for this card’s layout. */
    @SerializedName("layout") val layout: String,
    /** An object describing the legality of this card across play formats. Possible legalities are legal, not_legal, restricted, and banned. */
    @SerializedName("legalities") val cardLegalities: CardLegalities,
    /** The mana cost for this card. This value will be any empty string "" if the cost is absent.
     * Remember that per the game rules, a missing mana cost and a mana cost of {0} are different values. Multi-faced cards will report this value in card faces. */
    @SerializedName("mana_cost") override val manaCost: String?,
    /** This card’s foil Magic Online ID (also known as the Catalog ID), if any.
     * A large percentage of cards are not available on Magic Online and do not have this ID. */
    @SerializedName("mtgo_foil_id") val mtgoFoilId: Int?,
    /** This card’s Magic Online ID (also known as the Catalog ID), if any.
     * A large percentage of cards are not available on Magic Online and do not have this ID. */
    @SerializedName("mtgo_id") val mtgoId: Int?,
    /** This card’s multiverse IDs on Gatherer, if any, as an array of integers.
     * Note that Scryfall includes many promo cards, tokens, and other esoteric objects that do not have these identifiers. */
    @SerializedName("multiverse_ids") val multiverseIds: List<Int>?,
    /** The name of this card. If this card has multiple faces, this field will contain both names separated by ␣//␣. */
    @SerializedName("name") override val name: String,
    /**  */
    @SerializedName("nonfoil") override val nonfoil: Boolean?,
    /** A content type for this object, always card. (???) */
    @SerializedName("object") val objectX: String,
    /** A unique ID for this card’s oracle identity. This value is consistent across reprinted card editions,
     * and unique among different cards with the same name (tokens, Unstable variants, etc).
     * Always present except for the reversible_card layout where it will be absent;
     * oracle_id will be found on each face instead. */
    @SerializedName("oracle_id") val oracleId: String?,
    /** The Oracle text for this card, if any. */
    @SerializedName("oracle_text") val oracleText: String?,
    /** True if this card is oversized. */
    @SerializedName("oversized") val oversized: Boolean,
    /** This card’s rank/popularity on Penny Dreadful. Not all cards are ranked. */
    @SerializedName("penny_rank") override val pennyRank: Int?,
    /** This card’s power, if any. Note that some cards have powers that are not numeric, such as *. */
    @SerializedName("power") override val power: String?,
    /** An object containing daily price information for this card, including usd, usd_foil,
     * usd_etched, eur, eur_foil, eur_etched, and tix prices, as strings. */
    @SerializedName("prices") val cardPrices: CardPrices,
    /** Имя карты на не английском, если карта доступна на других языках */
    @SerializedName("printed_name") override val printedName: String?,
    /** A link to where you can begin paginating all re/prints for this card on Scryfall’s API. */
    @SerializedName("prints_search_uri") val printsSearchUri: String,
    /** Colors of mana that this card could produce. */
    @SerializedName("produced_mana") val producedMana: List<String>?,
    /** True if this card is a promotional print. */
    @SerializedName("promo") val promo: Boolean,
    /** An array of strings describing what categories of promo cards this card falls into. */
    @SerializedName("promo_types") val promoTypes: List<String>?,
    /** An object providing URIs to this card’s listing on major marketplaces. Omitted if the card is unpurchaseable. */
    @SerializedName("purchase_uris") val cardPurchaseUris: CardPurchaseUris?,
    /** This card’s rarity. One of common, uncommon, rare, special, mythic, or bonus. */
    @SerializedName("rarity") override val rarity: String,
    /** An object providing URIs to this card’s listing on other Magic: The Gathering online resources. */
    @SerializedName("related_uris") val cardRelatedUris: CardRelatedUris,
    /** The date this card was first released. */
    @SerializedName("released_at") override val releasedAt: String,
    /** True if this card is a reprint. */
    @SerializedName("reprint") val reprint: Boolean,
    /** True if this card is on the Reserved List. */
    @SerializedName("reserved") val reserved: Boolean,
    /** A link to this card’s rulings list on Scryfall’s API. */
    @SerializedName("rulings_uri") val rulingsUri: String,
    /** A link to this card’s set on Scryfall’s website. */
    @SerializedName("scryfall_set_uri") val scryfallSetUri: String,
    /** A link to this card’s permapage on Scryfall’s website. */
    @SerializedName("scryfall_uri") val scryfallUri: String,
    /** This card’s set code. */
    @SerializedName("set") override val setCode: String,
    /** This card’s Set object UUID. */
    @SerializedName("set_id") val setId: String,
    /** This card’s full set name. */
    @SerializedName("set_name") val setName: String,
    /** A link to where you can begin paginating this card’s set on the Scryfall API. */
    @SerializedName("set_search_uri") val setSearchUri: String,
    /** The type of set this printing is in. */
    @SerializedName("set_type") val setType: String,
    /** A link to this card’s set object on Scryfall’s API. */
    @SerializedName("set_uri") val setUri: String,
    /** True if this card is a Story Spotlight. */
    @SerializedName("story_spotlight") val storySpotlight: Boolean,
    /** This card’s ID on TCGplayer’s API, also known as the productId. */
    @SerializedName("tcgplayer_id") val tcgplayerId: Int?,
    /** True if the card is printed without text. */
    @SerializedName("textless") val textless: Boolean,
    /** This card’s toughness, if any. Note that some cards have toughnesses that are not numeric, such as *. */
    @SerializedName("toughness") override val toughness: String?,
    /** The type line of this card. */
    @SerializedName("type_line") val typeLine: String,
    /** A URI where you can retrieve a full object describing this card on Scryfall’s API. */
    @SerializedName("uri") override val uri: String,
    /** Whether this card is a variation of another printing. */
    @SerializedName("variation") val variation: Boolean,
) : CardEntity {

    override val imageDetailUri: String
        get() = cardImageUris?.png ?: ""

    override val imagePreviewUri: String
        get() = cardImageUris?.normal ?: ""

    override val priceInEur: String?
        get() = cardPrices.eur

    override val priceInTix: String?
        get() = cardPrices.tix

    override val priceInUsd: String?
        get() = cardPrices.usd

    override val cardPurchaseUrisEntity: CardPurchaseUrisEntity?
        get() = cardPurchaseUris?.let {
            CardPurchaseUrisEntity(
                cardhoarder = it.cardhoarder ?: "",
                cardmarket = it.cardmarket ?: "",
                tcgplayer = it.tcgplayer ?: "",
            )
        }

    override val cardRelatedUrisEntity: CardRelatedUrisEntity
        get() = cardRelatedUris.let {
            CardRelatedUrisEntity(
                edhrec = it.edhrec ?: "",
                gatherer = it.gatherer ?: "",
                tcgplayerInfiniteArticles = it.tcgplayerInfiniteArticles ?: "",
                tcgplayerInfiniteDecks = it.tcgplayerInfiniteDecks ?: "",
            )
        }

    override val availableCards: MutableList<AvailableCardEntity>
        get() = mutableListOf()
}