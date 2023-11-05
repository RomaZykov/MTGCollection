package com.andreikslpv.presentation_cards.recyclers

import com.andreikslpv.domain.entities.AvailableCardModel


interface AvailableItemClickListener {
    fun click(availableItem: AvailableCardModel)
}