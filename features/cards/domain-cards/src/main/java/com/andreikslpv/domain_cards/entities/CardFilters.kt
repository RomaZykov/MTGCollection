package com.andreikslpv.domain_cards.entities

import com.andreikslpv.domain.entities.CardLanguageV2

data class CardFilters(
    val codeOfSet: String,
    val lang: CardLanguageV2 = CardLanguageV2.ENGLISH,
    val sortsType: SortsType = SortsType.SET,
    val sortsTypeDir: SortsTypeDir = SortsTypeDir.ASC,
)
