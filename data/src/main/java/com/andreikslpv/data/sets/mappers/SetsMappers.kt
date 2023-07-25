package com.andreikslpv.data.sets.mappers

import com.andreikslpv.common.BaseMapper
import com.andreikslpv.data.sets.dto.sets.Set
import com.andreikslpv.data.sets.entities.SetDataModel
import com.andreikslpv.data.sets.entities.SetRoomModel

object SetsListDtoToDataModelMapper : BaseMapper<List<Set>, List<SetDataModel>> {
    override fun map(type: List<Set>?): List<SetDataModel> {
        return type?.map {
            SetDataModel(
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

object SetsListRoomToDataModelMapper : BaseMapper<List<SetRoomModel>, List<SetDataModel>> {
    override fun map(type: List<SetRoomModel>?): List<SetDataModel> {
        return type?.map {
            SetDataModel(
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

object SetsListDataToRoomModelMapper : BaseMapper<List<SetDataModel>, List<SetRoomModel>> {
    override fun map(type: List<SetDataModel>?): List<SetRoomModel> {
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