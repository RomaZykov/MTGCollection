package com.andreikslpv.data.cards.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.cards.dto.Card
import com.andreikslpv.data.cards.entities.CardDataModel

object CardsListDtoToDataModelMapper : BaseMapper<List<Card>, List<CardDataModel>> {
    override fun map(type: List<Card>?): List<CardDataModel> {
        return type?.map {
            CardDataModel(
                artist = it.artist,
                cmc = it.cmc,
                colorIdentity = it.colorIdentity,
                colors = it.colors,
                flavor = it.flavor ?: "",
                foreignNames = FnListDtoToDataModelMapper.map(it.foreignNames),
                id = it.id,
                imageUrl = it.imageUrl ?: "",
                layout = it.layout,
                manaCost = it.manaCost,
                multiverseid = it.multiverseid ?: "",
                name = it.name,
                number = it.number,
                originalText = it.originalText ?: "",
                originalType = it.originalType ?: "",
                power = it.power ?: "",
                printings = it.printings,
                rarity = it.rarity,
                set = it.set,
                setName = it.setName,
                subtypes = it.subtypes ?: emptyList(),
                text = it.text ?: "",
                toughness = it.toughness ?: "",
                type = it.type,
                types = it.types,
                watermark = it.watermark ?: "",
            )
        } ?: listOf()
    }
}