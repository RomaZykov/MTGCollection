package com.andreikslpv.domain_cards.entities

enum class SortsTypeDir(val dirApi: String, val dirRoom: Boolean) {
    ASC("asc", true),
    DESC("desc", false),
}