package com.andreikslpv.domain.entities

data class UserEntity(
    var uid: String = "",
    val collection: ArrayList<CardInCollectionEntity> = arrayListOf(),
    val history: ArrayList<String> = arrayListOf(),
)

data class CardInCollectionEntity(
    val id: String,
    val setCode: String,
    val availableCards: ArrayList<AvailableCardEntity> = arrayListOf()
)