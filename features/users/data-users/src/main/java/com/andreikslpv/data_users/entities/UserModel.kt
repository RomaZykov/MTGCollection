package com.andreikslpv.data_users.entities

import com.andreikslpv.data.db.entities.CardFirebaseEntity

data class UserModel(
    var uid: String = "",
    val collection: ArrayList<String> = arrayListOf(),
    val history: ArrayList<CardFirebaseEntity> = arrayListOf(),
)
