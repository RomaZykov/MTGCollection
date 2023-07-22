package com.andreikslpv.mtgcollection.glue.sets

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.sets.entities.SetLocalModel
import com.andreikslpv.sets.domain.entities.SetFeatureModel

object SetLocalToFeatureModelMapper : BaseMapper<SetLocalModel, SetFeatureModel> {
    override fun map(type: SetLocalModel?): SetFeatureModel {
        return SetFeatureModel(
                block = type?.block ?: "",
                code = type?.code ?: "",
                name = type?.name ?: "",
                onlineOnly = type?.onlineOnly ?: false,
                releaseDate = type?.releaseDate ?: "",
                type = type?.type ?: "",
            )

    }
}