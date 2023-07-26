package com.andreikslpv.mtgcollection.glue.sets

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.sets.domain.entities.SetFeatureModel

object SetDataToFeatureModelMapper : BaseMapper<SetDataModel, SetFeatureModel> {
    override fun map(type: SetDataModel?): SetFeatureModel {
        return SetFeatureModel(
            block = type?.block ?: "",
            code = type?.code ?: "",
            name = type?.name ?: "",
            onlineOnly = type?.onlineOnly ?: false,
            releaseDate = type?.releaseDate ?: "",
            type = type?.type ?: "",
            symbolUrl = type?.symbolUrl ?: "",
            cardCount = type?.cardCount ?: 0,
        )

    }
}