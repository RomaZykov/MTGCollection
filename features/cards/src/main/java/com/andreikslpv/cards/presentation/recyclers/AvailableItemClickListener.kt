package com.andreikslpv.cards.presentation.recyclers

import com.andreikslpv.domain.entities.AvailableCardFeatureModel


interface AvailableItemClickListener {
    fun click(availableItem: com.andreikslpv.domain.entities.AvailableCardFeatureModel)
}