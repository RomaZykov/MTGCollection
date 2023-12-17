package com.andreikslpv.domain.entities

import java.io.Serializable

data class ForeignNameEntity(
    val imageUrl: String = "",
    val language: String = "",
    val multiverseid: Int = 0,
    val name: String = "",
): Serializable
