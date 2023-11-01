package com.andreikslpv.domain_users

import com.andreikslpv.domain.entities.CardModel

data class UserModel(
    var uid: String = "",
    val collection: ArrayList<String> = arrayListOf(),
    val history: ArrayList<CardModel> = arrayListOf(),
)
