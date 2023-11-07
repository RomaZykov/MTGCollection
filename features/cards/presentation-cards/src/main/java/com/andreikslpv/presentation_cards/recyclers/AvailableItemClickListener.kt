package com.andreikslpv.presentation_cards.recyclers

import com.andreikslpv.domain.entities.AvailableCardEntity


interface AvailableItemClickListener {
    fun click(availableItem: AvailableCardEntity)
}