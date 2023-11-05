package com.andreikslpv.data_sets.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.db.entities.SetRoomModel
import com.andreikslpv.data_sets.dto.Set
import com.andreikslpv.domain_sets.entities.SetModel

object SetsListDtoToDataModelMapper : BaseMapper<List<Set>, List<SetModel>> {
    override fun map(type: List<Set>?): List<SetModel> {
        return type?.map {
            SetModel(
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

object SetsListRoomToDataModelMapper : BaseMapper<List<SetRoomModel>, List<SetModel>> {
    override fun map(type: List<SetRoomModel>?): List<SetModel> {
        return type?.map {
            SetModel(
                block = it.block,
                code = it.code,
                name = it.name,
                onlineOnly = it.onlineOnly,
                releaseDate = it.releaseDate,
                type = it.type,
                symbolUrl = it.symbolUrl,
                cardCount = it.cardCount,
            )
        } ?: listOf()
    }
}

object SetsListDataToRoomModelMapper : BaseMapper<List<SetModel>, List<SetRoomModel>> {
    override fun map(type: List<SetModel>?): List<SetRoomModel> {
        return type?.map {
            SetRoomModel(
                block = it.block,
                code = it.code,
                name = it.name,
                onlineOnly = it.onlineOnly,
                releaseDate = it.releaseDate,
                type = it.type,
                symbolUrl = it.symbolUrl,
                cardCount = it.cardCount,
            )
        } ?: listOf()
    }
}