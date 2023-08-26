package com.andreikslpv.data.cards.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.cards.dto.ForeignName
import com.andreikslpv.data.cards.entities.ForeignNameDataModel

object FnListDtoToDataModelMapper : BaseMapper<List<ForeignName>, List<ForeignNameDataModel>> {
    override fun map(type: List<ForeignName>?): List<ForeignNameDataModel> {
        return type?.map {
            ForeignNameDataModel(
                flavor = it.flavor ?: "",
                imageUrl = it.imageUrl ?: "",
                language = it.language,
                multiverseid = it.multiverseid,
                name = it.name,
                text = it.text ?: "",
                type = it.type ?: "",
            )
        } ?: listOf()
    }
}