package com.andreikslpv.mtgcollection.glue.cards

import com.andreikslpv.cards.domain.entities.AvailableCardFeatureModel
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.entities.ForeignNameFeatureModel
import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.cards.entities.AvailableCardDataModel
import com.andreikslpv.data.cards.entities.CardDataModel
import com.andreikslpv.data.cards.entities.ForeignNameDataModel

object CardDataToFeatureModelMapper : BaseMapper<CardDataModel, CardFeatureModel> {
    override fun map(type: CardDataModel?): CardFeatureModel {
        return CardFeatureModel(
            availableCards = AcDataToFeatureModelMapper.map(type?.availableCards).toMutableList(),
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

    object FnDataToFeatureModelMapper :
        BaseMapper<List<ForeignNameDataModel>, List<ForeignNameFeatureModel>> {
        override fun map(type: List<ForeignNameDataModel>?): List<ForeignNameFeatureModel> {
            return type?.map {
                ForeignNameFeatureModel(
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
        BaseMapper<List<AvailableCardDataModel>, List<AvailableCardFeatureModel>> {
        override fun map(type: List<AvailableCardDataModel>?): List<AvailableCardFeatureModel> {
            return type?.map {
                AvailableCardFeatureModel(
                    language = it.language,
                    count = it.count,
                    foiled = it.foiled,
                    condition = it.condition,
                )
            } ?: listOf()
        }
    }
}

object CardFeatureToDataModelMapper : BaseMapper<CardFeatureModel, CardDataModel> {
    override fun map(type: CardFeatureModel?): CardDataModel {
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

    object FnFeatureToDataModelMapper :
        BaseMapper<List<ForeignNameFeatureModel>, List<ForeignNameDataModel>> {
        override fun map(type: List<ForeignNameFeatureModel>?): List<ForeignNameDataModel> {
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
        BaseMapper<List<AvailableCardFeatureModel>, List<AvailableCardDataModel>> {
        override fun map(type: List<AvailableCardFeatureModel>?): List<AvailableCardDataModel> {
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
}