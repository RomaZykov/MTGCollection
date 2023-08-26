package com.andreikslpv.data.cards.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.cards.dto.Card
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.cards.entities.CardRoomModel
import com.andreikslpv.data.cards.entities.ForeignNameDataModel

object CardsListDtoToDataModelMapper : BaseMapper<List<Card>, List<CardDataModel>> {
    override fun map(type: List<Card>?): List<CardDataModel> {
        return type?.map {
            CardDataModel(
                artist = it.artist ?: "",
                cmc = it.cmc ?: 0.0,
                colorIdentity = it.colorIdentity ?: emptyList(),
                colors = it.colors ?: emptyList(),
                flavor = it.flavor ?: "",
                foreignNames = FnListDtoToDataModelMapper.map(it.foreignNames),
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

object CardsListRoomToDataModelMapper : BaseMapper<List<CardRoomModel>, List<CardDataModel>> {
    override fun map(type: List<CardRoomModel>?): List<CardDataModel> {
        return type?.map {
            CardDataModel(
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

    private fun fnStringToListConverter(fnString: String): List<ForeignNameDataModel> {
        val result = mutableListOf<ForeignNameDataModel>()
        val temp = fnString.split("@@")
        temp.forEach {
            val fn = it.split("##")
            if (fn.size >= 3) {
                val foreignName = ForeignNameDataModel(
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

object CardsListDataToRoomModelMapper : BaseMapper<List<CardDataModel>, List<CardRoomModel>> {
    override fun map(type: List<CardDataModel>?): List<CardRoomModel> {
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

    private fun fnListToStringConverter(fnList: List<ForeignNameDataModel>): String {
        var result = ""
        fnList.forEach { fn ->
            val temp = "${fn.language}##${fn.name}##${fn.imageUrl}"
            result += "$temp@@"
        }
        return result.trimEnd('@').trimEnd('@')
    }
}