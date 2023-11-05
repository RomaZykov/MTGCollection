package com.andreikslpv.data_cards.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.db.entities.CardRoomModel
import com.andreikslpv.data_cards.dto.Card
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain.entities.ForeignNameModel

object CardsListDtoToFeatureModelMapper : BaseMapper<List<Card>, List<CardModel>> {
    override fun map(type: List<Card>?): List<CardModel> {
        return type?.map {
            CardModel(
                artist = it.artist ?: "",
                cmc = it.cmc ?: 0.0,
                colorIdentity = it.colorIdentity ?: emptyList(),
                colors = it.colors ?: emptyList(),
                flavor = it.flavor ?: "",
                foreignNames = FnListDtoToFeatureModelMapper.map(it.foreignNames),
                id = it.id,
                imageUrl = it.imageUrl ?: "",
                layout = it.layout ?: "",
                manaCost = it.manaCost ?: "",
                multiverseid = it.multiverseid ?: "",
                name = it.name ?: "",
                number = it.number ?: "",
                originalText = it.originalText ?: "",
                originalType = it.originalType ?: "",
                power = it.power ?: "",
                printings = it.printings ?: emptyList(),
                rarity = it.rarity ?: "",
                set = it.set ?: "",
                setName = it.setName ?: "",
                subtypes = it.subtypes ?: emptyList(),
                text = it.text ?: "",
                toughness = it.toughness ?: "",
                type = it.type ?: "",
                types = it.types ?: emptyList(),
                watermark = it.watermark ?: "",
            )
        } ?: listOf()
    }
}

object CardsListRoomToFeatureModelMapper : BaseMapper<List<CardRoomModel>, List<CardModel>> {
    override fun map(type: List<CardRoomModel>?): List<CardModel> {
        return type?.map {
            CardModel(
                id = it.id,
                imageUrl = it.imageUrl,
                name = it.name,
                number = it.number,
                set = it.set,
                setName = it.setName,
                foreignNames = fnStringToListConverter(it.foreignNames)
            )
        } ?: listOf()
    }

    private fun fnStringToListConverter(fnString: String): List<ForeignNameModel> {
        val result = mutableListOf<ForeignNameModel>()
        val temp = fnString.split("@@")
        temp.forEach {
            val fn = it.split("##")
            if (fn.size >= 3) {
                val foreignName = ForeignNameModel(
                    language = fn[0],
                    name = fn[1],
                    imageUrl = fn[2]
                )
                result.add(foreignName)
            }
        }
        return result
    }
}

object CardsListFeatureToRoomModelMapper : BaseMapper<List<CardModel>, List<CardRoomModel>> {
    override fun map(type: List<CardModel>?): List<CardRoomModel> {
        return type?.map {
            CardRoomModel(
                id = it.id,
                imageUrl = it.imageUrl,
                name = it.name,
                number = it.number,
                set = it.set,
                setName = it.setName,
                foreignNames = fnListToStringConverter(it.foreignNames)
            )
        } ?: listOf()
    }

    private fun fnListToStringConverter(fnList: List<ForeignNameModel>): String {
        var result = ""
        fnList.forEach { fn ->
            val temp = "${fn.language}##${fn.name}##${fn.imageUrl}"
            result += "$temp@@"
        }
        return result.trimEnd('@').trimEnd('@')
    }
}