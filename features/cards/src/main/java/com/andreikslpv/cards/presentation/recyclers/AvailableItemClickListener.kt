package com.andreikslpv.cards.presentation.recyclers

import com.andreikslpv.common_impl.entities.AvailableCardFeatureModel


interface AvailableItemClickListener {
    fun click(availableItem: AvailableCardFeatureModel)
}