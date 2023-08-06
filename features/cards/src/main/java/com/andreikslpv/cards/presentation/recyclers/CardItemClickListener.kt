package com.andreikslpv.cards.presentation.recyclers

import com.andreikslpv.cards.domain.entities.CardFeatureModel

interface CardItemClickListener {
    fun click(set: CardFeatureModel)
}