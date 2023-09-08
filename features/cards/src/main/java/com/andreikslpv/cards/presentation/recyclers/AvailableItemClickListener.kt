package com.andreikslpv.cards.presentation.recyclers

import com.andreikslpv.cards.domain.entities.AvailableCardFeatureModel


interface AvailableItemClickListener {
    fun click(availableItem: AvailableCardFeatureModel)
}