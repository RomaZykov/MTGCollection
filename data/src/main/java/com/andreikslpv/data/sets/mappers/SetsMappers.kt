package com.andreikslpv.data.sets.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.sets.dto.sets.Set
import com.andreikslpv.data.sets.entities.SetLocalModel

object SetsListDtoToLocalModelMapper : BaseMapper<List<Set>, List<SetLocalModel>> {
    override fun map(type: List<Set>?): List<SetLocalModel> {
        return type?.map {
            SetLocalModel(
                block = it.block ?: "",
                code = it.code,
                name = it.name,
                onlineOnly = it.onlineOnly,
                releaseDate = it.releaseDate,
                type = it.type,
            )
        } ?: listOf()
    }
}