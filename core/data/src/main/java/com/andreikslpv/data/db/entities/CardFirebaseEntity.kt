package com.andreikslpv.data.db.entities

import com.andreikslpv.domain.entities.AvailableCardEntity
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.ForeignNameEntity

data class CardFirebaseEntity(
    override val cmc: Double?,
    override val colorIdentity: List<String>?,
    override val colors: List<String>?,
    override val foreignNamesList: List<ForeignNameEntity>,
    override val id: String,
    override val imageUrl: String?,
    override val manaCost: String?,
    override val multiverseid: String?,
    override val name: String?,
    override val orderedNumber: String,
    override val power: String?,
    override val printings: List<String>?,
    override val rarity: String?,
    override val set: String?,
    override val setName: String?,
    override val subtypes: List<String>?,
    override val text: String?,
    override val toughness: String?,
    override val type: String?,
    override val types: List<String>?,
    override val availableCards: MutableList<AvailableCardEntity>,
): CardEntity {

    constructor() : this(
        cmc = 0.0,
        colorIdentity = listOf(),
        colors = listOf(),
        foreignNamesList = listOf(),
        id = "",
        imageUrl = "",
        manaCost = "",
        multiverseid = "",
        name = "",
        orderedNumber = "",
        power = "",
        printings = listOf(),
        rarity = "",
        set = "",
        setName = "",
        subtypes = listOf(),
        text = "",
        toughness = "",
        type = "",
        types = listOf(),
        availableCards = mutableListOf(),
    )

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
        availableCards = card.availableCards,
    )
}
