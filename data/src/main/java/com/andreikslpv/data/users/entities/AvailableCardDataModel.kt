package com.andreikslpv.data.users.entities

data class AvailableCardDataModel(
    val id: String = "",
    val count: Int = 0,
    val language: String = "",
    val foiled: Boolean = false,
    val condition: String = "",
    var ownerUid: String = "",
)
