package com.andreikslpv.datasource_room_cards

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreikslpv.domain.entities.CardPreviewEntity
import com.andreikslpv.domain.entities.CardPurchaseUrisEntity
import com.andreikslpv.domain.entities.CardRelatedUrisEntity
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_ARTIST
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_CMC
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_COLLECTOR_NUMBER
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_EDHREC_RANK
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_LANG
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_NAME
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_PENNY_RANK
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_POWER
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_RARITY
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_SET_CODE
import com.andreikslpv.domain_cards.CardsConstants.COLUMN_TOUGHNESS

/**
 * Database (Room) entity for storing Cards in the local storage.
 */

@Entity(tableName = CardsRoomConstants.TABLE_CARDS)
data class CardPreviewRoomEntity(
    @ColumnInfo(name = COLUMN_ARTIST) override val artist: String,
    @ColumnInfo(name = "card_back_id") override val cardBackId: String,
    @ColumnInfo(name = COLUMN_CMC) override val cmc: Double,
    @ColumnInfo(name = COLUMN_COLLECTOR_NUMBER) override val collectorNumber: String,
    @ColumnInfo(name = "color_identity") override val colorIdentity: List<String>,
    @ColumnInfo(name = "colors") override val colors: List<String>,
    @ColumnInfo(name = COLUMN_EDHREC_RANK) override val edhrecRank: Int,
    @ColumnInfo(name = "finishes") override val finishes: List<String>,
    @ColumnInfo(name = "foil") override val foil: Boolean,
    @ColumnInfo(name = "highres_image") override val highresImage: Boolean,
    @PrimaryKey
    @ColumnInfo(name = "id") override val id: String,
    @ColumnInfo(name = "image_status") override val imageStatus: String,
    @ColumnInfo(name = "image_detail_uri") override val imageDetailUri: String,
    @ColumnInfo(name = "image_preview_uri") override val imagePreviewUri: String,
    @ColumnInfo(name = COLUMN_LANG) override val lang: String,
    @ColumnInfo(name = "manaCost") override val manaCost: String,
    @ColumnInfo(name = COLUMN_NAME) override val name: String,
    @ColumnInfo(name = "nonfoil") override val nonfoil: Boolean,
    @ColumnInfo(name = COLUMN_PENNY_RANK) override val pennyRank: Int,
    @ColumnInfo(name = COLUMN_POWER) override val power: String,
    @ColumnInfo(name = "price_in_eur") override val priceInEur: String,
    @ColumnInfo(name = "price_in_tix") override val priceInTix: String,
    @ColumnInfo(name = "price_in_usd") override val priceInUsd: String,
    @ColumnInfo(name = "printed_name") override val printedName: String,
    @ColumnInfo(name = COLUMN_RARITY) override val rarity: String,
    @ColumnInfo(name = "released_at") override val releasedAt: String,
    @ColumnInfo(index = true, name = COLUMN_SET_CODE) override val setCode: String,
    @ColumnInfo(name = COLUMN_TOUGHNESS) override val toughness: String,
    @ColumnInfo(name = "uri") override val uri: String,
    @Embedded override val cardPurchaseUrisEntity: CardPurchaseUrisEntity,
    @Embedded override val cardRelatedUrisEntity: CardRelatedUrisEntity,
) : CardPreviewEntity {

    constructor(card: CardPreviewEntity?) : this(
        artist = card?.artist ?: "",
        cardBackId = card?.cardBackId ?: "",
        cmc = card?.cmc ?: 0.0,
        collectorNumber = card?.collectorNumber ?: "",
        colorIdentity = card?.colorIdentity ?: listOf(),
        colors = card?.colors ?: listOf(),
        edhrecRank = card?.edhrecRank ?: 0,
        finishes = card?.finishes ?: listOf(),
        foil = card?.foil ?: false,
        highresImage = card?.highresImage ?: false,
        id = card?.id ?: "",
        imageStatus = card?.imageStatus ?: "",
        imageDetailUri = card?.imageDetailUri ?: "",
        imagePreviewUri = card?.imagePreviewUri ?: "",
        lang = card?.lang ?: "",
        manaCost = card?.manaCost ?: "",
        name = card?.name ?: "",
        nonfoil = card?.nonfoil ?: true,
        pennyRank = card?.pennyRank ?: 0,
        power = card?.power ?: "",
        priceInEur = card?.priceInEur ?: "",
        priceInTix = card?.priceInTix ?: "",
        priceInUsd = card?.priceInUsd ?: "",
        printedName = card?.printedName ?: "",
        cardPurchaseUrisEntity = card?.cardPurchaseUrisEntity ?: CardPurchaseUrisEntity(),
        rarity = card?.rarity ?: "",
        cardRelatedUrisEntity = card?.cardRelatedUrisEntity ?: CardRelatedUrisEntity(),
        releasedAt = card?.releasedAt ?: "",
        setCode = card?.setCode ?: "",
        toughness = card?.toughness ?: "",
        uri = card?.uri ?: "",
    )
}