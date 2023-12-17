package com.andreikslpv.domain.entities

import java.io.Serializable

/**
 * Represents data from [CardEntity] + state in coolection.
 */

data class CardUiEntity(
    override val cmc: Double = 0.0,
    override val colorIdentity: List<String> = listOf(),
    override val colors: List<String> = listOf(),
    override val foreignNamesList: List<ForeignNameEntity> = listOf(),
    override val id: String = "",
    override val imageUrl: String = "",
    override val manaCost: String = "",
    override val multiverseid: String = "",
    override val name: String = "",
    override val orderedNumber: String = "",
    override val power: String = "",
    override val printings: List<String> = listOf(),
    override val rarity: String = "",
    override val set: String = "",
    override val setName: String = "",
    override val subtypes: List<String> = listOf(),
    override val text: String = "",
    override val toughness: String = "",
    override val type: String = "",
    override val types: List<String> = listOf(),
    override val availableCards: MutableList<AvailableCardEntity> = mutableListOf(),
    val isInCollection: Boolean = false,
) : CardEntity, Serializable {

    constructor(card:CardEntity?, isInCollection: Boolean) : this(
        cmc = card?.cmc ?: 0.0,
        colorIdentity = card?.colorIdentity ?: listOf(),
        colors = card?.colors ?: listOf(),
        foreignNamesList = card?.foreignNamesList ?: listOf(),
        id = card?.id ?: "",
        imageUrl = card?.imageUrl ?: "",
        manaCost = card?.manaCost ?: "",
        multiverseid = card?.multiverseid ?: "",
        name = card?.name ?: "",
        orderedNumber = card?.orderedNumber ?: "",
        power = card?.power ?: "",
        printings = card?.printings ?: listOf(),
        rarity = card?.rarity ?: "",
        set = card?.set ?: "",
        setName = card?.setName ?: "",
        subtypes = card?.subtypes ?: listOf(),
        text = card?.text ?: "",
        toughness = card?.toughness ?: "",
        type = card?.type ?: "",
        types = card?.types ?: listOf(),
        availableCards = card?.availableCards ?: mutableListOf(),
        isInCollection = isInCollection,
    )

}
