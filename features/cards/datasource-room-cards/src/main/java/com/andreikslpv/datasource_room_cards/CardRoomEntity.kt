package com.andreikslpv.datasource_room_cards

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreikslpv.datasource_room_cards.CardsRoomConstants.TABLE_CARDS
import com.andreikslpv.domain.entities.AvailableCardEntity
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.ForeignNameEntity

/**
 * Database (Room) entity for storing Cards in the local storage.
 */

@Entity(tableName = TABLE_CARDS)
data class CardRoomEntity(
    override val cmc: Double,
    override val colorIdentity: List<String>,
    override val colors: List<String>,
    override val foreignNamesList: List<ForeignNameEntity>,
    @PrimaryKey override val id: String,
    override val imageUrl: String,
    override val manaCost: String,
    override val multiverseid: String,
    override val name: String,
    override val orderedNumber: String,
    override val power: String,
    override val printings: List<String>,
    override val rarity: String,
    @ColumnInfo(index = true) override val set: String,
    override val setName: String,
    override val subtypes: List<String>,
    override val text: String,
    override val toughness: String,
    override val type: String,
    override val availableCards: MutableList<AvailableCardEntity>,
    override val types: List<String>,
) : CardEntity {

    constructor(card: CardEntity) : this(
        cmc = card.cmc ?: 0.0,
        colorIdentity = card.colorIdentity ?: listOf(),
        colors = card.colors ?: listOf(),
        foreignNamesList = card.foreignNamesList,
        id = card.id,
        imageUrl = card.imageUrl ?: "",
        manaCost = card.manaCost ?: "",
        multiverseid = card.multiverseid ?: "",
        name = card.name ?: "",
        orderedNumber = card.orderedNumber,
        power = card.power ?: "",
        printings = card.printings ?: listOf(),
        rarity = card.rarity ?: "",
        set = card.set ?: "",
        setName = card.setName ?: "",
        subtypes = card.subtypes ?: listOf(),
        text = card.text ?: "",
        toughness = card.toughness ?: "",
        type = card.type ?: "",
        types = card.types ?: listOf(),
        availableCards = card.availableCards ?: mutableListOf(),
    )

}
