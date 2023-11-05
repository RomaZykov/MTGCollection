package com.andreikslpv.data_cards.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data_cards.entities.AvailableCardDataModel
import com.andreikslpv.data_cards.entities.CardDataModel
import com.andreikslpv.data_cards.entities.ForeignNameDataModel
import com.andreikslpv.domain.entities.AvailableCardModel
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain.entities.ForeignNameModel

object CardsListDataToFeatureModelMapper : BaseMapper<List<CardDataModel>, List<CardModel>> {
    override fun map(type: List<CardDataModel>?): List<CardModel> {
        return type?.map {
            CardModel(
                availableCards = AcDataToFeatureModelMapper.map(it.availableCards).toMutableList(),
                artist = it.artist,
                cmc = it.cmc,
                colorIdentity = it.colorIdentity,
                colors = it.colors,
                flavor = it.flavor,
                foreignNames = FnDataToFeatureModelMapper.map(it.foreignNames),
                id = it.id,
                imageUrl = it.imageUrl,
                layout = it.layout,
                manaCost = it.manaCost,
                multiverseid = it.multiverseid,
                name = it.name,
                number = it.number,
                originalText = it.originalText,
                originalType = it.originalType,
                power = it.power,
                printings = it.printings,
                rarity = it.rarity,
                set = it.set,
                setName = it.setName,
                subtypes = it.subtypes,
                text = it.text,
                toughness = it.toughness,
                type = it.type,
                types = it.types,
                watermark = it.watermark,
            )
        } ?: listOf()
    }
}

object CardDataToFeatureModelMapper : BaseMapper<CardDataModel, CardModel> {
    override fun map(type: CardDataModel?): CardModel {
        return CardModel(
            availableCards = AcDataToFeatureModelMapper.map(type?.availableCards)
                .toMutableList(),
            artist = type?.artist ?: "",
            cmc = type?.cmc ?: 0.0,
            colorIdentity = type?.colorIdentity ?: emptyList(),
            colors = type?.colors ?: emptyList(),
            flavor = type?.flavor ?: "",
            foreignNames = FnDataToFeatureModelMapper.map(type?.foreignNames),
            id = type?.id ?: "",
            imageUrl = type?.imageUrl ?: "",
            layout = type?.layout ?: "",
            manaCost = type?.manaCost ?: "",
            multiverseid = type?.multiverseid ?: "",
            name = type?.name ?: "",
            number = type?.number ?: "",
            originalText = type?.originalText ?: "",
            originalType = type?.originalType ?: "",
            power = type?.power ?: "",
            printings = type?.printings ?: emptyList(),
            rarity = type?.rarity ?: "",
            set = type?.set ?: "",
            setName = type?.setName ?: "",
            subtypes = type?.subtypes ?: emptyList(),
            text = type?.text ?: "",
            toughness = type?.toughness ?: "",
            type = type?.type ?: "",
            types = type?.types ?: emptyList(),
            watermark = type?.watermark ?: "",
        )
    }
}

object FnDataToFeatureModelMapper :
    BaseMapper<List<ForeignNameDataModel>, List<ForeignNameModel>> {
    override fun map(type: List<ForeignNameDataModel>?): List<ForeignNameModel> {
        return type?.map {
            ForeignNameModel(
                flavor = it.flavor ?: "",
                imageUrl = it.imageUrl,
                language = it.language,
                multiverseid = it.multiverseid,
                name = it.name,
                text = it.text,
                type = it.type,
            )
        } ?: listOf()
    }
}

object AcDataToFeatureModelMapper :
    BaseMapper<List<AvailableCardDataModel>, List<AvailableCardModel>> {
    override fun map(type: List<AvailableCardDataModel>?): List<AvailableCardModel> {
        return type?.map {
            AvailableCardModel(
                language = it.language,
                count = it.count,
                foiled = it.foiled,
                condition = it.condition,
            )
        } ?: listOf()
    }
}

// FeatureToData

object CardListFeatureToDataModelMapper : BaseMapper<List<CardModel>, List<CardDataModel>> {
    override fun map(type: List<CardModel>?): List<CardDataModel> {
        return type?.map {
            CardDataModel(
                availableCards = AcFeatureToDataModelMapper.map(it.availableCards),
                artist = it.artist,
                cmc = it.cmc,
                colorIdentity = it.colorIdentity,
                colors = it.colors,
                flavor = it.flavor,
                foreignNames = FnFeatureToDataModelMapper.map(it.foreignNames),
                id = it.id,
                imageUrl = it.imageUrl,
                layout = it.layout,
                manaCost = it.manaCost,
                multiverseid = it.multiverseid,
                name = it.name,
                number = it.number,
                originalText = it.originalText,
                originalType = it.originalType,
                power = it.power,
                printings = it.printings,
                rarity = it.rarity,
                set = it.set,
                setName = it.setName,
                subtypes = it.subtypes,
                text = it.text,
                toughness = it.toughness,
                type = it.type,
                types = it.types,
                watermark = it.watermark,
            )
        } ?: listOf()
    }
}

object CardFeatureToDataModelMapper : BaseMapper<CardModel, CardDataModel> {
    override fun map(type: CardModel?): CardDataModel {
        return CardDataModel(
            availableCards = AcFeatureToDataModelMapper.map(type?.availableCards),
            artist = type?.artist ?: "",
            cmc = type?.cmc ?: 0.0,
            colorIdentity = type?.colorIdentity ?: emptyList(),
            colors = type?.colors ?: emptyList(),
            flavor = type?.flavor ?: "",
            foreignNames = FnFeatureToDataModelMapper.map(type?.foreignNames),
            id = type?.id ?: "",
            imageUrl = type?.imageUrl ?: "",
            layout = type?.layout ?: "",
            manaCost = type?.manaCost ?: "",
            multiverseid = type?.multiverseid ?: "",
            name = type?.name ?: "",
            number = type?.number ?: "",
            originalText = type?.originalText ?: "",
            originalType = type?.originalType ?: "",
            power = type?.power ?: "",
            printings = type?.printings ?: emptyList(),
            rarity = type?.rarity ?: "",
            set = type?.set ?: "",
            setName = type?.setName ?: "",
            subtypes = type?.subtypes ?: emptyList(),
            text = type?.text ?: "",
            toughness = type?.toughness ?: "",
            type = type?.type ?: "",
            types = type?.types ?: emptyList(),
            watermark = type?.watermark ?: "",
        )
    }
}

object FnFeatureToDataModelMapper :
    BaseMapper<List<ForeignNameModel>, List<ForeignNameDataModel>> {
    override fun map(type: List<ForeignNameModel>?): List<ForeignNameDataModel> {
        return type?.map {
            ForeignNameDataModel(
                flavor = it.flavor ?: "",
                imageUrl = it.imageUrl,
                language = it.language,
                multiverseid = it.multiverseid,
                name = it.name,
                text = it.text,
                type = it.type,
            )
        } ?: listOf()
    }
}

object AcFeatureToDataModelMapper :
    BaseMapper<List<AvailableCardModel>, List<AvailableCardDataModel>> {
    override fun map(type: List<AvailableCardModel>?): List<AvailableCardDataModel> {
        return type?.map {
            AvailableCardDataModel(
                language = it.language,
                count = it.count,
                foiled = it.foiled,
                condition = it.condition,
            )
        } ?: listOf()
    }
}