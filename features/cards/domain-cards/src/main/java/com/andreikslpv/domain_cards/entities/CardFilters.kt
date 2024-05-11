package com.andreikslpv.domain_cards.entities

import com.andreikslpv.domain.entities.CardLanguage

data class CardFilters(
    val codeOfSet: String,
    val lang: CardLanguage = CardLanguage.ENGLISH,
    val sortsType: SortsType = SortsType.SET,
    val sortsTypeDir: SortsTypeDir = SortsTypeDir.ASC,
)
