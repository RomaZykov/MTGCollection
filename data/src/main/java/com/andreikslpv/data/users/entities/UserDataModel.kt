package com.andreikslpv.data.users.entities

data class UserDataModel(
    var uid: String = "",
    val collection: ArrayList<String> = arrayListOf(),
    val history: ArrayList<String> = arrayListOf(),
)
