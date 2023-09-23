package com.andreikslpv.data.users.entities

import com.andreikslpv.data.cards.entities.CardDataModel

data class UserDataModel(
    var uid: String = "",
    val collection: ArrayList<String> = arrayListOf(),
    val history: ArrayList<CardDataModel> = arrayListOf(),
)
