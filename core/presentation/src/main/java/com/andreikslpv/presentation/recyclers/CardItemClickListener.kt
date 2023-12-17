package com.andreikslpv.presentation.recyclers

import com.andreikslpv.domain.entities.CardUiEntity


interface CardItemClickListener {
    fun click(card: CardUiEntity)
}