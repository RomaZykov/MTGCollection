package com.andreikslpv.cards.domain.entities

enum class CardCondition(val fullName: String, val shortName: String) {
    NONE("", ""),
    M("Mint", "M"),
    NM("Near mint", "NM"),
    SP("Slightly played", "SP"),
    MP("Moderately played", "MP"),
    PL("Played", "PL"),
    HP("Heavily played", "HP"),
    DM("Damaged", "DM"),
}