package com.andreikslpv.data_cards.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data_cards.dto.ForeignName
import com.andreikslpv.domain.entities.ForeignNameModel

object FnListDtoToFeatureModelMapper : BaseMapper<List<ForeignName>, List<ForeignNameModel>> {
    override fun map(type: List<ForeignName>?): List<ForeignNameModel> {
        return type?.map {
            ForeignNameModel(
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